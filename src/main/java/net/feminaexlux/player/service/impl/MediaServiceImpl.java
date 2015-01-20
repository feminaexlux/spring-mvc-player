package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.Tables;
import net.feminaexlux.player.model.tables.Music;
import net.feminaexlux.player.model.tables.Resource;
import net.feminaexlux.player.service.MediaService;
import net.feminaexlux.player.type.MediaType;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaServiceImpl implements MediaService {

	public static final Music    MUSIC    = Tables.MUSIC.as("m");
	public static final Resource RESOURCE = Tables.RESOURCE.as("r");

	@Autowired
	private DSLContext database;

	@Override
	public Result<Record> search(final String query) {
		return database.select(MUSIC.fields()).from(RESOURCE)
				.join(MUSIC).on(MUSIC.RESOURCE.eq(RESOURCE.CHECKSUM))
				.where(RESOURCE.NAME.contains(query))
				.or(MUSIC.TITLE.contains(query))
				.or(MUSIC.ARTIST.contains(query))
				.or(MUSIC.ALBUM.contains(query))
				.orderBy(MUSIC.ARTIST, MUSIC.ALBUM, MUSIC.TITLE)
				.fetch();
	}

	@Override
	public Result<Record> recentlyPlayed(final MediaType type, int max) {
		return database.select(MUSIC.fields()).from(RESOURCE)
				.join(MUSIC).on(MUSIC.RESOURCE.eq(RESOURCE.CHECKSUM))
				.orderBy(RESOURCE.LASTACCESSED.desc())
				.limit(max)
				.fetch();
	}
}
