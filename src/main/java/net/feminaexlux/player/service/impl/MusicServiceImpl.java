package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.tables.records.MusicRecord;
import net.feminaexlux.player.service.MusicService;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static net.feminaexlux.player.model.Tables.MUSIC;
import static net.feminaexlux.player.model.Tables.RESOURCE;

@Service
public class MusicServiceImpl implements MusicService {

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
	public Result<Record> recentlyPlayed(final int max) {
		return database.select(MUSIC.fields()).from(RESOURCE)
				.join(MUSIC).on(MUSIC.RESOURCE.eq(RESOURCE.CHECKSUM))
				.where(RESOURCE.LASTACCESSED.isNotNull())
				.orderBy(RESOURCE.LASTACCESSED.desc())
				.limit(max)
				.fetch();
	}

	@Override
	public Result<Record1<String>> findAllArtists() {
		return findAllBy(MUSIC.ARTIST);
	}

	@Override
	public Result<Record> findByArtist(final String artist) {
		return findBy(MUSIC.ARTIST, artist);
	}

	@Override
	public Result<Record1<String>> findAllAlbums() {
		return findAllBy(MUSIC.ALBUM);
	}

	@Override
	public Result<Record> findByAlbum(final String album) {
		return findBy(MUSIC.ALBUM, album);
	}

	private Result<Record1<String>> findAllBy(final TableField<MusicRecord, String> field) {
		return database.selectDistinct(field)
				.from(MUSIC)
				.orderBy(field)
				.fetch();
	}

	private Result<Record> findBy(final TableField<MusicRecord, String> field, final String term) {
		return database.select(MUSIC.fields()).from(MUSIC)
				.where(field.equal(term))
				.orderBy(MUSIC.ARTIST, MUSIC.ALBUM, MUSIC.TRACK)
				.fetch();
	}
}
