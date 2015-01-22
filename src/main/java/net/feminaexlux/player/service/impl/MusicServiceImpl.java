package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.tables.records.MusicRecord;
import net.feminaexlux.player.service.MusicService;
import org.jooq.DSLContext;
import org.jooq.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static net.feminaexlux.player.model.Tables.MUSIC;
import static net.feminaexlux.player.model.Tables.RESOURCE;

@Service
public class MusicServiceImpl implements MusicService {

	@Autowired
	private DSLContext database;

	@Override
	public List<MusicRecord> search(final String query) {
		return database.select(MUSIC.fields()).from(RESOURCE)
				.join(MUSIC).on(MUSIC.RESOURCE.eq(RESOURCE.CHECKSUM))
				.where(RESOURCE.NAME.contains(query))
				.or(MUSIC.TITLE.contains(query))
				.or(MUSIC.ARTIST.contains(query))
				.or(MUSIC.ALBUM.contains(query))
				.orderBy(MUSIC.ARTIST, MUSIC.ALBUM, MUSIC.TITLE)
				.fetchInto(MusicRecord.class);
	}

	@Override
	public List<MusicRecord> recentlyPlayed(final int max) {
		return database.select(MUSIC.fields()).from(RESOURCE)
				.join(MUSIC).on(MUSIC.RESOURCE.eq(RESOURCE.CHECKSUM))
				.where(RESOURCE.LASTACCESSED.isNotNull())
				.orderBy(RESOURCE.LASTACCESSED.desc())
				.limit(max)
				.fetchInto(MusicRecord.class);
	}

	@Override
	public List<String> findAllArtists() {
		return findAllBy(MUSIC.ARTIST);
	}

	@Override
	public List<MusicRecord> findByArtist(final String artist) {
		return findBy(MUSIC.ARTIST, artist);
	}

	@Override
	public List<String> findAllAlbums() {
		return findAllBy(MUSIC.ALBUM);
	}

	@Override
	public List<MusicRecord> findByAlbum(final String album) {
		return findBy(MUSIC.ALBUM, album);
	}

	private List<String> findAllBy(final TableField<MusicRecord, String> field) {
		return database.selectDistinct(field)
				.from(MUSIC)
				.orderBy(field)
				.fetchInto(String.class);
	}

	private List<MusicRecord> findBy(final TableField<MusicRecord, String> field, final String term) {
		return database.select(MUSIC.fields()).from(MUSIC)
				.where(field.equal(term))
				.orderBy(MUSIC.ARTIST, MUSIC.ALBUM, MUSIC.TRACK)
				.fetchInto(MusicRecord.class);
	}
}
