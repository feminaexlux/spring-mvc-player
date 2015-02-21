package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.table.record.DirectoryRecord;
import net.feminaexlux.player.model.table.record.MusicRecord;
import net.feminaexlux.player.model.table.record.ResourceRecord;
import net.feminaexlux.player.model.type.MediaType;
import net.feminaexlux.player.service.LibraryService;
import net.feminaexlux.player.util.Normalizer;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static net.feminaexlux.player.model.Table.DIRECTORY;
import static net.feminaexlux.player.model.Table.MUSIC;
import static net.feminaexlux.player.model.Table.RESOURCE;
import static net.feminaexlux.player.model.Table.TYPE_EXTENSION;

@Service
public class LibraryServiceImpl implements LibraryService {

	private static final Logger LOG = LoggerFactory.getLogger(LibraryService.class);
	private static final int BATCH_AMOUNT = Integer.parseInt(System.getProperty("player.sql.batch", "500"));

	@Autowired
	protected DSLContext database;

	private List<MusicRecord> modifiedMusic = new ArrayList<>();
	private List<ResourceRecord> modifiedResources = new ArrayList<>();
	private Set<String> textForNormalizing = new HashSet<>();

	@Async
	@Override
	public void buildLibrary(final String directory, final MediaType type) throws IOException {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		database.insertInto(DIRECTORY, DIRECTORY.DIRECTORY_, DIRECTORY.TYPE, DIRECTORY.LAST_SCANNED)
				.values(directory, type.name(), now)
				.onDuplicateKeyUpdate()
				.set(DIRECTORY.LAST_SCANNED, now)
				.execute();

		LibraryWalker walker = new LibraryWalker(directory, type);
		Files.walkFileTree(Paths.get(directory), walker);

		database.batchStore(modifiedResources).execute();
		database.batchStore(modifiedMusic).execute();
	}

	@Override
	public void clearLibrary(final String directory, final MediaType type) {
		List<MusicRecord> affectedMusic = database
				.select(MUSIC.fields()).from(MUSIC)
				.join(RESOURCE).on(MUSIC.CHECKSUM.equal(RESOURCE.CHECKSUM))
				.where(RESOURCE.DIRECTORY.equal(directory))
				.fetchInto(MusicRecord.class);
		database.batchDelete(affectedMusic);
	}

	@Override
	public void updateAllLibraries() throws IOException {
		List<DirectoryRecord> directories = database.fetch(DIRECTORY);
		for (DirectoryRecord directoryRecord : directories) {
			buildLibrary(directoryRecord.getDirectory(), MediaType.find(directoryRecord.getType()));
		}
	}

	protected class LibraryWalker extends SimpleFileVisitor<Path> {

		private static final String UNKNOWN = "(Unknown)";

		private String directory;
		private List<String> validExtensions = Collections.emptyList();

		private Map<String, MusicRecord> pathToMusicRecord = new HashMap<>();
		private Map<String, MusicRecord> checksumToMusic = Collections.emptyMap();
		private Map<String, ResourceRecord> checksumToResource = Collections.emptyMap();

		protected LibraryWalker(final String directory, final MediaType mediaType) {
			super();

			this.directory = directory;

			validExtensions = database.select(TYPE_EXTENSION.EXTENSION.upper())
					.from(TYPE_EXTENSION)
					.where(TYPE_EXTENSION.TYPE.equal(mediaType.name()))
					.fetchInto(String.class);

			initializeMaps();
		}

		private void initializeMaps() {
			checksumToMusic = database.fetch(MUSIC).intoMap(MUSIC.CHECKSUM);
			checksumToResource = database.fetch(RESOURCE).intoMap(RESOURCE.CHECKSUM);
			for (MusicRecord musicRecord : checksumToMusic.values()) {
				ResourceRecord resourceRecord = checksumToResource.get(musicRecord.getChecksum());
				String filePath = resourceRecord.getDirectory() + resourceRecord.getPath();
				pathToMusicRecord.put(filePath, musicRecord);
			}
		}

		@Override
		public FileVisitResult visitFile(final Path file, final BasicFileAttributes attributes) throws IOException {
			if (attributes.isRegularFile() && isRightMediaType(file)) {
				LOG.trace("Filename is {}", file);

				try {
					String hash = hash(file);

					ResourceRecord resource = getNewOrExistingResource(hash);
					if (resource.getLastUpdated() != null && resource.getLastUpdated().getTime() > file.toFile().lastModified()) {
						return FileVisitResult.CONTINUE;
					}

					updateResourceProperties(file, hash);
					updateMusicProperties(file, hash);
				} catch (Exception e) {
					LOG.error("Exception insert/updating media file {}\n{}", file, e);
				}
			}

			return FileVisitResult.CONTINUE;
		}

		private boolean isRightMediaType(final Path file) {
			String filePath = file.toString();
			String extension = filePath.substring(filePath.lastIndexOf(".") + 1);

			return validExtensions.contains(extension.toUpperCase());
		}

		private String hash(final Path file) throws NoSuchAlgorithmException, IOException {
			if (pathToMusicRecord.containsKey(file.toString())) {
				return pathToMusicRecord.get(file.toString()).getChecksum();
			}

			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] fileBytes = FileUtils.readFileToByteArray(file.toFile());
			byte[] fileHash = digest.digest(fileBytes);
			return Hex.encodeHexString(fileHash);
		}

		private void updateResourceProperties(final Path file, final String hash) {
			ResourceRecord resourceRecord = getNewOrExistingResource(hash);
			resourceRecord.setType(MediaType.MUSIC.name());
			resourceRecord.setChecksum(hash);
			resourceRecord.setDirectory(directory);
			resourceRecord.setPath(file.toString().substring(directory.length()));
			resourceRecord.setLastUpdated(new Timestamp(System.currentTimeMillis()));
			modifiedResources.add(resourceRecord);
			flushResourceRecordBatch();
		}

		private ResourceRecord getNewOrExistingResource(final String hash) {
			ResourceRecord resourceRecord = checksumToResource.get(hash);
			if (resourceRecord == null) {
				resourceRecord = new ResourceRecord();
				checksumToResource.put(hash, resourceRecord);
			}

			return resourceRecord;
		}

		private void flushResourceRecordBatch() {
			if (modifiedResources.size() == BATCH_AMOUNT) {
				database.batchStore(modifiedResources).execute();
				modifiedResources.clear();
			}
		}

		private void updateMusicProperties(final Path file, final String hash) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
			AudioFile audioFile = AudioFileIO.read(file.toFile());
			Tag tag = audioFile.getTag();
			if (tag != null) {
				String artist = getArtist(tag);
				String album = getAlbum(tag);
				Integer trackNumber = getTrackNumber(tag);
				String title = getTitle(tag, hash);
				String genre = getGenre(tag);

				MusicRecord musicRecord = getNewOrExistingMusic(hash);
				musicRecord.setArtist(artist);
				musicRecord.setArtistUrl(Normalizer.normalizeForUrl(artist));
				musicRecord.setAlbum(album);
				musicRecord.setAlbumUrl(Normalizer.normalizeForUrl(album));
				musicRecord.setTrackNumber(trackNumber.byteValue());
				musicRecord.setTitle(title);
				musicRecord.setGenre(genre);

				modifiedMusic.add(musicRecord);
				flushMusicRecordBatch();
				addTextForNormalizing(musicRecord);
			}
		}

		private MusicRecord getNewOrExistingMusic(final String hash) {
			MusicRecord musicRecord = checksumToMusic.get(hash);
			if (musicRecord == null) {
				musicRecord = new MusicRecord();
				musicRecord.setChecksum(hash);
				checksumToMusic.put(hash, musicRecord);
			}

			return musicRecord;
		}

		private String getArtist(final Tag tag) {
			if (StringUtils.isNotEmpty(tag.getFirst(FieldKey.ARTIST))) {
				return tag.getFirst(FieldKey.ARTIST).trim();
			} else if (StringUtils.isNotEmpty(tag.getFirst(FieldKey.ALBUM_ARTIST))) {
				return tag.getFirst(FieldKey.ALBUM_ARTIST).trim();
			}

			return UNKNOWN;
		}

		private String getAlbum(final Tag tag) {
			if (StringUtils.isNotEmpty(tag.getFirst(FieldKey.ALBUM))) {
				return tag.getFirst(FieldKey.ALBUM).trim();
			}

			return UNKNOWN;
		}

		private Integer getTrackNumber(final Tag tag) {
			String trackNumber = tag.getFirst(FieldKey.TRACK);
			if (StringUtils.isNotEmpty(trackNumber)) {
				return Integer.parseInt(trackNumber);
			}

			return null;
		}

		private String getTitle(final Tag tag, final String hash) {
			if (StringUtils.isNotEmpty(tag.getFirst(FieldKey.TITLE))) {
				return tag.getFirst(FieldKey.TITLE).trim();
			}

			if (checksumToResource.containsKey(hash)) {
				return checksumToResource.get(hash).getPath();
			}

			return UNKNOWN;
		}

		private String getGenre(final Tag tag) {
			return tag.getFirst(FieldKey.GENRE);
		}

		private void flushMusicRecordBatch() {
			if (modifiedMusic.size() == BATCH_AMOUNT) {
				database.batchStore(modifiedMusic).execute();
				modifiedMusic.clear();
			}
		}

		private void addTextForNormalizing(final MusicRecord musicRecord) {
			textForNormalizing.add(musicRecord.getArtist());
			textForNormalizing.add(musicRecord.getAlbum());
			textForNormalizing.add(musicRecord.getTitle());
			textForNormalizing.add(musicRecord.getGenre());
		}

		@Override
		public FileVisitResult visitFileFailed(final Path file, final IOException exception) throws IOException {
			LOG.error("Error visiting file {}\n{}", file.toString(), exception);
			return FileVisitResult.CONTINUE;
		}
	}
}
