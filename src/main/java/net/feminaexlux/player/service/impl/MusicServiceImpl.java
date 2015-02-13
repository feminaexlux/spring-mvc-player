package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.container.MusicResource;
import net.feminaexlux.player.model.table.NormalizedText;
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
import static net.feminaexlux.player.model.Table.NORMALIZED_TEXT;
import static net.feminaexlux.player.model.Table.RESOURCE;

@Service
public class MusicServiceImpl implements MusicService {

	@Autowired
	private DSLContext database;

	@Override
	public List<MusicRecord> search(final String query) {
		NormalizedText artistText = NORMALIZED_TEXT.as("artistText");
		NormalizedText albumText = NORMALIZED_TEXT.as("albumText");
		NormalizedText titleText = NORMALIZED_TEXT.as("titleText");
		NormalizedText genreText = NORMALIZED_TEXT.as("genreText");

		return database.selectDistinct(MUSIC.fields()).from(RESOURCE)
				.join(MUSIC).on(MUSIC.RESOURCE.eq(RESOURCE.CHECKSUM))
				.join(artistText).on(MUSIC.ARTIST.equal(artistText.ORIGINAL))
				.join(albumText).on(MUSIC.ARTIST.equal(albumText.ORIGINAL))
				.join(titleText).on(MUSIC.ARTIST.equal(titleText.ORIGINAL))
				.join(genreText).on(MUSIC.ARTIST.equal(genreText.ORIGINAL))
				.where(RESOURCE.NAME.contains(query))
				.or(MUSIC.TITLE.contains(query))
				.or(MUSIC.ARTIST.contains(query))
				.or(MUSIC.ALBUM.contains(query))
				.or(MUSIC.GENRE.contains(query))
				.or(artistText.ORIGINAL.contains(query))
				.or(albumText.ORIGINAL.contains(query))
				.or(titleText.ORIGINAL.contains(query))
				.or(genreText.ORIGINAL.contains(query))
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
	public Map<String, String> findAllArtists() {
		return findAllBy(MUSIC.ARTIST);
	}

	@Override
	public List<MusicRecord> findByArtist(final String artist) {
		return findBy(MUSIC.ARTIST, artist);
	}

	@Override
	public Map<String, String> findAllAlbums() {
		return findAllBy(MUSIC.ALBUM);
	}

	@Override
	public List<MusicRecord> findByAlbum(final String album) {
		return findBy(MUSIC.ALBUM, album);
	}

	private Map<String, String> findAllBy(final TableField<MusicRecord, String> field) {
		return database.selectDistinct(field, NORMALIZED_TEXT.NORMALIZED)
				.from(MUSIC)
				.leftOuterJoin(NORMALIZED_TEXT).on(NORMALIZED_TEXT.ORIGINAL.equal(field))
				.orderBy(buildOrderBy(field))
				.fetchMap(field, NORMALIZED_TEXT.NORMALIZED);
	}

	private List<MusicRecord> findBy(final TableField<MusicRecord, String> field, final String term) {
		return database.select(MUSIC.fields()).from(MUSIC)
				.leftOuterJoin(NORMALIZED_TEXT).on(NORMALIZED_TEXT.ORIGINAL.equal(field))
				.where(field.equal(term))
				.or(NORMALIZED_TEXT.NORMALIZED.equal(term))
				.orderBy(buildOrderBy(field))
				.fetchInto(MusicRecord.class);
	}

	private Field[] buildOrderBy(final TableField<MusicRecord, String> field) {
		List<Field> orderBy = new ArrayList<>();

		if (field.equals(MUSIC.ARTIST)) {
			orderBy.add(MUSIC.ARTIST);
		}

		orderBy.add(MUSIC.ALBUM);
		orderBy.add(MUSIC.TRACK);
		orderBy.add(MUSIC.TITLE);

		return orderBy.toArray(new Field[0]);
	}

	@Override
	public MusicResource find(final String checksum) {
		MusicRecord musicRecord = database.fetchOne(MUSIC, MUSIC.RESOURCE.equal(checksum));
		ResourceRecord resourceRecord = database.fetchOne(RESOURCE, RESOURCE.CHECKSUM.equal(checksum));
		return new MusicResource(musicRecord, resourceRecord);
	}

	@Override
	public List<MusicResource> findAll() {
		Map<String, MusicRecord> music = database.fetch(MUSIC).intoMap(MUSIC.RESOURCE, MusicRecord.class);
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
		resourceRecord.setLastaccessed(new Timestamp(System.currentTimeMillis()));
		database.executeUpdate(resourceRecord);
	}

	@Override
	public void update(final List<MusicRecord> musicRecords) {
		database.batchUpdate(musicRecords).execute();
	}
}
