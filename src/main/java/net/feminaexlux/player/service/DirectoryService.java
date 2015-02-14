package net.feminaexlux.player.service;

import net.feminaexlux.player.model.table.record.DirectoryRecord;
import net.feminaexlux.player.model.type.MediaType;

import java.util.List;

public interface DirectoryService {

	List<DirectoryRecord> allDirectories();

	List<DirectoryRecord> findByType(final MediaType mediaType);

	List<DirectoryRecord> search(final String query);

}
