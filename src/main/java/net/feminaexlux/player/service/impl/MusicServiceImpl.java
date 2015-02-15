package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.container.MusicResource;
import net.feminaexlux.player.model.table.record.MusicRecord;
import net.feminaexlux.player.model.table.record.ResourceRecord;
import net.feminaexlux.player.service.MusicService;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.feminaexlux.player.model.Table.MUSIC;
import static net.feminaexlux.player.model.Table.RESOURCE;

@Service
public class MusicServiceImpl implements MusicService {

	@Autowired
	private DSLContext database;

	@Override
	public List<MusicRecord> search(final String query) {
		return database.selectDistinct(MUSIC.fields()).from(RESOURCE)
				.join(MUSIC).on(MUSIC.CHECKSUM.eq(RESOURCE.CHECKSUM))
				.where(RESOURCE.PATH.contains(query))
				.or(MUSIC.TITLE.contains(query))
				.or(MUSIC.ARTIST.contains(query))
				.or(MUSIC.ARTIST_URL.contains(query))
				.or(MUSIC.ALBUM.contains(query))
				.or(MUSIC.ALBUM_URL.contains(query))
				.or(MUSIC.GENRE.contains(query))
				.orderBy(MUSIC.ARTIST, MUSIC.ALBUM, MUSIC.TITLE)
				.fetchInto(MusicRecord.class);
	}

	@Override
	public List<MusicRecord> recentlyPlayed(final int max) {
		return database.select(MUSIC.fields()).from(RESOURCE)
				.join(MUSIC).on(MUSIC.CHECKSUM.eq(RESOURCE.CHECKSUM))
				.where(RESOURCE.LAST_UPDATED.isNotNull())
				.orderBy(RESOURCE.LAST_UPDATED.desc())
				.limit(max)
				.fetchInto(MusicRecord.class);
	}

	@Override
	public Map<String, String> findAllArtists() {
		return database.select(MUSIC.ARTIST, MUSIC.ARTIST_URL)
				.from(MUSIC)
				.orderBy(MUSIC.ARTIST)
				.fetchMap(MUSIC.ARTIST, MUSIC.ARTIST_URL);
	}

	@Override
	public List<MusicRecord> findByArtistUrl(final String artist) {
		return database.selectFrom(MUSIC)
				.where(MUSIC.ARTIST_URL.equal(artist))
				.fetchInto(MusicRecord.class);
	}

	@Override
	public Map<String, String> findAllAlbums() {
		return database.select(MUSIC.ALBUM, MUSIC.ALBUM_URL)
				.from(MUSIC)
				.orderBy(MUSIC.ALBUM)
				.fetchMap(MUSIC.ALBUM, MUSIC.ALBUM_URL);
	}

	@Override
	public List<MusicRecord> findByAlbumUrl(final String album) {
		return database.selectFrom(MUSIC)
				.where(MUSIC.ALBUM_URL.equal(album))
				.orderBy(buildOrderBy(MUSIC.ALBUM))
				.fetchInto(MusicRecord.class);
	}

	private Field[] buildOrderBy(final TableField<MusicRecord, String> field) {
		List<Field> orderBy = new ArrayList<>();

		if (field.equals(MUSIC.ARTIST)) {
			orderBy.add(MUSIC.ARTIST);
		}

		orderBy.add(MUSIC.ALBUM);
		orderBy.add(MUSIC.TRACK_NUMBER);
		orderBy.add(MUSIC.TITLE);

		return orderBy.toArray(new Field[0]);
	}

	@Override
	public MusicResource find(final String checksum) {
		MusicRecord musicRecord = database.fetchOne(MUSIC, MUSIC.CHECKSUM.equal(checksum));
		ResourceRecord resourceRecord = database.fetchOne(RESOURCE, RESOURCE.CHECKSUM.equal(checksum));
		return new MusicResource(musicRecord, resourceRecord);
	}

	@Override
	public List<MusicResource> findAll() {
		Map<String, MusicRecord> music = database.fetch(MUSIC).intoMap(MUSIC.CHECKSUM, MusicRecord.class);
		Map<String, ResourceRecord> resources = database.fetch(RESOURCE).intoMap(RESOURCE.CHECKSUM, ResourceRecord.class);

		List<MusicResource> musicResources = new ArrayList<>();
		for (Map.Entry<String, MusicRecord> musicRecord : music.entrySet()) {
			ResourceRecord resource = resources.get(musicRecord.getKey());
			musicResources.add(new MusicResource(musicRecord.getValue(), resource));
		}

		return musicResources;
	}

	@Override
	public void setPlayed(final String checksum) {
		ResourceRecord resourceRecord = database.fetchOne(RESOURCE, RESOURCE.CHECKSUM.equal(checksum));
		resourceRecord.setLastUpdated(new Timestamp(System.currentTimeMillis()));
		database.executeUpdate(resourceRecord);
	}

	@Override
	public void update(final List<MusicRecord> musicRecords) {
		database.batchUpdate(musicRecords).execute();
	}
}
