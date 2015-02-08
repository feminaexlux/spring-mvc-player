package net.feminaexlux.player.service;

import net.feminaexlux.player.model.type.MediaType;

import java.io.IOException;

public interface DirectoryScannerService {

	void buildLibrary(final String directory, final MediaType type) throws IOException;

	void clearLibrary(final String directory, final MediaType type);

	void updateAllLibraries() throws IOException;

}
