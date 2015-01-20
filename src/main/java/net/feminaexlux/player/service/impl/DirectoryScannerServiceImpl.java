package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.tables.TypeExtension;
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
import org.jooq.Record1;
import org.jooq.Result;
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
import java.util.List;
import java.util.stream.Collectors;

import static net.feminaexlux.player.model.Tables.DIRECTORY;
import static net.feminaexlux.player.model.Tables.MUSIC;
import static net.feminaexlux.player.model.Tables.RESOURCE;
import static net.feminaexlux.player.model.Tables.TYPE_EXTENSION;

@Service
public class DirectoryScannerServiceImpl implements DirectoryScannerService {

	private static final Logger LOG = LoggerFactory.getLogger(DirectoryScannerService.class);

	@Autowired
	protected DSLContext database;

	@Override
	public void buildLibrary(final String directory, final MediaType type) throws IOException {
		database.insertInto(DIRECTORY, DIRECTORY.LOCATION, DIRECTORY.TYPE, DIRECTORY.LASTSCANNED)
				.values(directory, type.name(), new Timestamp(System.currentTimeMillis()))
				.onDuplicateKeyIgnore()
				.execute();

		LibraryWalker walker = new LibraryWalker(directory, type);
		Files.walkFileTree(Paths.get(directory), walker);
	}

	@Override
	public void updateLibrary(final String directory, final MediaType type) {

	}

	@Override
	public void clearLibrary(final String directory, final MediaType type) {

	}

	protected class LibraryWalker extends SimpleFileVisitor<Path> {

		private String directory;
		private List<String> validExtensions = Collections.emptyList();

		protected LibraryWalker(final String directory, final MediaType mediaType) {
			super();

			this.directory = directory;

			TypeExtension te = TYPE_EXTENSION.as("te");
			Result<Record1<String>> result = database.select(te.EXTENSION)
					.from(te)
					.where(te.TYPE.equal(mediaType.name()))
					.fetch();

			validExtensions = result.stream()
					.map(record -> record.getValue(te.EXTENSION).toUpperCase())
					.collect(Collectors.toList());
		}

		@Override
		public FileVisitResult visitFile(final Path file, final BasicFileAttributes attributes) throws IOException {
			if (attributes.isRegularFile() && isRightMediaType(file)) {
				try {
					String hash = hash(file);
					database.insertInto(RESOURCE, RESOURCE.CHECKSUM, RESOURCE.DIRECTORY, RESOURCE.NAME)
							.values(hash, directory, file.toString().substring(directory.length()))
							.onDuplicateKeyIgnore()
							.execute();

					readMusicTag(file, hash);
				} catch (CannotReadException | TagException | ReadOnlyFileException | InvalidAudioFrameException | NoSuchAlgorithmException e) {
					e.printStackTrace();
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
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] fileBytes = FileUtils.readFileToByteArray(file.toFile());
			byte[] fileHash = digest.digest(fileBytes);
			return Hex.encodeHexString(fileHash);
		}

		private void readMusicTag(final Path file, final String hash) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
			AudioFile audioFile = AudioFileIO.read(file.toFile());
			Tag tag = audioFile.getTag();
			if (tag != null) {
				database.insertInto(MUSIC, MUSIC.RESOURCE, MUSIC.ARTIST, MUSIC.ALBUM, MUSIC.TITLE, MUSIC.GENRE, MUSIC.TRACK)
						.values(hash,
								getArtist(tag),
								tag.getFirst(FieldKey.ALBUM),
								tag.getFirst(FieldKey.TITLE),
								tag.getFirst(FieldKey.GENRE),
								getTrackNumber(tag))
						.onDuplicateKeyIgnore()
						.execute();
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
