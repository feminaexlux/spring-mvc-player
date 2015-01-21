package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.tables.records.MusicRecord;
import net.feminaexlux.player.model.tables.records.ResourceRecord;
import net.feminaexlux.player.service.DirectoryScannerService;
import net.feminaexlux.player.type.MediaType;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.feminaexlux.player.model.Tables.DIRECTORY;
import static net.feminaexlux.player.model.Tables.MUSIC;
import static net.feminaexlux.player.model.Tables.RESOURCE;
import static net.feminaexlux.player.model.Tables.TYPE_EXTENSION;

@Service
public class DirectoryScannerServiceImpl implements DirectoryScannerService {

	private static final Logger LOG = LoggerFactory.getLogger(DirectoryScannerService.class);

	@Autowired
	protected DSLContext database;

	private Map<String, MusicRecord> pathToMusicRecord = new HashMap<>();
	private Map<String, MusicRecord> checksumToMusicRecord = Collections.emptyMap();
	private Map<String, ResourceRecord> checksumToResourceRecord = Collections.emptyMap();

	@Override
	public void buildLibrary(final String directory, final MediaType type) throws IOException {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		database.insertInto(DIRECTORY, DIRECTORY.LOCATION, DIRECTORY.TYPE, DIRECTORY.LASTSCANNED)
				.values(directory, type.name(), now)
				.onDuplicateKeyUpdate()
				.set(DIRECTORY.LASTSCANNED, now)
				.execute();

		initializeMaps();

		LibraryWalker walker = new LibraryWalker(directory, type);
		Files.walkFileTree(Paths.get(directory), walker);

		database.batchStore(checksumToResourceRecord.values());
		database.batchStore(checksumToMusicRecord.values());
	}

	private void initializeMaps() {
		checksumToMusicRecord = database.fetch(MUSIC).intoMap(MUSIC.RESOURCE);
		checksumToResourceRecord = database.fetch(RESOURCE).intoMap(RESOURCE.CHECKSUM);
		for (MusicRecord musicRecord : checksumToMusicRecord.values()) {
			ResourceRecord resourceRecord = checksumToResourceRecord.get(musicRecord.getResource());
			String filePath = resourceRecord.getDirectory() + resourceRecord.getName();
			pathToMusicRecord.put(filePath, musicRecord);
		}
	}

	@Override
	public void clearLibrary(final String directory, final MediaType type) {
		List<MusicRecord> affectedMusic = database
				.select(MUSIC.fields()).from(MUSIC)
				.join(RESOURCE).on(MUSIC.RESOURCE.equal(RESOURCE.CHECKSUM))
				.where(RESOURCE.DIRECTORY.equal(directory))
				.fetchInto(MusicRecord.class);
		database.batchDelete(affectedMusic);

		List<ResourceRecord> affectedResources = database
				.select(RESOURCE.fields()).from(RESOURCE)
				.where(RESOURCE.DIRECTORY.equal(directory))
				.fetchInto(ResourceRecord.class);
		database.batchDelete(affectedResources);
	}

	protected class LibraryWalker extends SimpleFileVisitor<Path> {

		private String directory;
		private List<String> validExtensions = Collections.emptyList();

		protected LibraryWalker(final String directory, final MediaType mediaType) {
			super();

			this.directory = directory;

			validExtensions = database.select(TYPE_EXTENSION.EXTENSION.upper())
					.from(TYPE_EXTENSION)
					.where(TYPE_EXTENSION.TYPE.equal(mediaType.name()))
					.fetch()
					.into(String.class);
		}

		@Override
		public FileVisitResult visitFile(final Path file, final BasicFileAttributes attributes) throws IOException {
			if (attributes.isRegularFile() && isRightMediaType(file)) {
				try {
					String hash = hash(file);
					updateResourceProperties(file, hash);
					updateMusicProperties(file, hash);
				} catch (CannotReadException | TagException | ReadOnlyFileException | InvalidAudioFrameException | NoSuchAlgorithmException e) {
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
				return pathToMusicRecord.get(file.toString()).getResource();
			}

			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] fileBytes = FileUtils.readFileToByteArray(file.toFile());
			byte[] fileHash = digest.digest(fileBytes);
			return Hex.encodeHexString(fileHash);
		}

		private void updateResourceProperties(Path file, String hash) {
			ResourceRecord resourceRecord = checksumToResourceRecord.get(hash);
			if (resourceRecord == null) {
				resourceRecord = new ResourceRecord();
				checksumToResourceRecord.put(hash, resourceRecord);
			}
			resourceRecord.setChecksum(hash);
			resourceRecord.setDirectory(directory);
			resourceRecord.setName(file.toString().substring(directory.length()));
		}

		private void updateMusicProperties(final Path file, final String hash) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
			AudioFile audioFile = AudioFileIO.read(file.toFile());
			Tag tag = audioFile.getTag();
			if (tag != null) {
				String artist = getArtist(tag);
				String album = tag.getFirst(FieldKey.ALBUM);
				Integer trackNumber = getTrackNumber(tag);
				String title = tag.getFirst(FieldKey.TITLE);
				String genre = tag.getFirst(FieldKey.GENRE);

				MusicRecord musicRecord = checksumToMusicRecord.get(hash);
				if (musicRecord == null) {
					musicRecord = new MusicRecord();
					musicRecord.setResource(hash);
					checksumToMusicRecord.put(hash, musicRecord);
				}

				musicRecord.setArtist(artist);
				musicRecord.setAlbum(album);
				musicRecord.setTrack(trackNumber);
				musicRecord.setTitle(title);
				musicRecord.setGenre(genre);
			}
		}

		private String getArtist(final Tag tag) {
			if (StringUtils.isNotEmpty(tag.getFirst(FieldKey.ARTIST))) {
				return tag.getFirst(FieldKey.ARTIST);
			} else if (StringUtils.isNotEmpty(tag.getFirst(FieldKey.ALBUM_ARTIST))) {
				return tag.getFirst(FieldKey.ALBUM_ARTIST);
			}

			return "Unknown";
		}

		private Integer getTrackNumber(final Tag tag) {
			String trackNumber = tag.getFirst(FieldKey.TRACK);
			if (StringUtils.isNotEmpty(trackNumber)) {
				return Integer.parseInt(trackNumber);
			}

			return null;
		}

		@Override
		public FileVisitResult visitFileFailed(final Path file, final IOException exception) throws IOException {
			LOG.error("Error visiting file {}\n{}", file.toString(), exception);
			return FileVisitResult.CONTINUE;
		}
	}
}
