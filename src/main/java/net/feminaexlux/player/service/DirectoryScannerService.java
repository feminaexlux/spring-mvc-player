package net.feminaexlux.player.service;

import net.feminaexlux.player.model.tables.Directory;
import net.feminaexlux.player.type.MediaType;

import java.io.IOException;

public interface DirectoryScannerService {

	void buildLibrary(final String directory, final MediaType type) throws IOException;

	void updateLibrary(final Directory directory, final MediaType type);

	void clearLibrary(final Directory directory, final MediaType type);

}
