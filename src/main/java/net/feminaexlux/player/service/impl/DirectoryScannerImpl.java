package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.tables.Directory;
import net.feminaexlux.player.model.tables.TypeExtension;
import net.feminaexlux.player.service.DirectoryScanner;
import net.feminaexlux.player.type.MediaType;
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
import java.util.ArrayList;
import java.util.List;

import static net.feminaexlux.player.model.Tables.TYPE_EXTENSION;

@Service
public class DirectoryScannerImpl implements DirectoryScanner {

	private static final Logger LOG = LoggerFactory.getLogger(DirectoryScanner.class);

	@Autowired
	protected DSLContext database;

	@Override
	public void buildLibrary(final String directory, final MediaType type) throws IOException {
		LibraryWalker walker = new LibraryWalker(type);
		Files.walkFileTree(Paths.get(directory), walker);
	}

	@Override
	public void updateLibrary(final Directory directory, final MediaType type) {

	}

	@Override
	public void clearLibrary(final Directory directory, final MediaType type) {

	}

	protected class LibraryWalker extends SimpleFileVisitor<Path> {

		private List<String> validExtensions = new ArrayList<>();

		protected LibraryWalker(final MediaType mediaType) {
			super();

			TypeExtension te = TYPE_EXTENSION.as("te");
			Result<Record1<String>> result = database.select(te.EXTENSION)
					.from(te)
					.where(te.TYPE.equal(mediaType.name()))
					.fetch();

			for (Record1 record : result) {
				validExtensions.add(record.getValue(te.EXTENSION).toUpperCase());
			}
		}

		@Override
		public FileVisitResult visitFile(final Path file, final BasicFileAttributes attributes) throws IOException {
			if (attributes.isRegularFile()) {
				System.out.println("Regular file: " + file.toString());

				if (isRightMediaType(file)) {
					// insert into media database...
				}
			}

			return FileVisitResult.CONTINUE;
		}

		private boolean isRightMediaType(final Path file) {
			String filePath = file.toString();
			String extension = filePath.substring(filePath.lastIndexOf("."));

			return validExtensions.contains(extension.toUpperCase());
		}

		@Override
		public FileVisitResult visitFileFailed(final Path file, final IOException exception) throws IOException {
			LOG.error("Error visiting file {}\n{}", file.toString(), exception);
			return FileVisitResult.CONTINUE;
		}
	}
}
