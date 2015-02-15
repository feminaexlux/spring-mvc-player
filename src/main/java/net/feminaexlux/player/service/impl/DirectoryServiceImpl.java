package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.table.record.DirectoryRecord;
import net.feminaexlux.player.model.type.MediaType;
import net.feminaexlux.player.service.DirectoryService;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static net.feminaexlux.player.model.table.Directory.DIRECTORY;

@Service
public class DirectoryServiceImpl implements DirectoryService {

	@Autowired
	private DSLContext database;

	@Override
	public List<DirectoryRecord> allDirectories() {
		return database.fetch(DIRECTORY)
				.into(DirectoryRecord.class);
	}

	@Override
	public List<DirectoryRecord> findByType(final MediaType mediaType) {
		return database.select()
				.from(DIRECTORY)
				.where(DIRECTORY.TYPE.equalIgnoreCase(mediaType.name()))
				.fetchInto(DirectoryRecord.class);
	}

	@Override
	public List<DirectoryRecord> search(final String query) {
		return database.select()
				.from(DIRECTORY)
				.where(DIRECTORY.DIRECTORY_.contains(query))
				.fetchInto(DirectoryRecord.class);
	}
}
