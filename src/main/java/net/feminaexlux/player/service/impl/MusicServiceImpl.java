package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.tables.NormalizedText;
import net.feminaexlux.player.model.tables.records.MusicRecord;
import net.feminaexlux.player.model.tables.records.ResourceRecord;
import net.feminaexlux.player.service.MusicService;
import org.apache.commons.lang3.ArrayUtils;
import org.jooq.DSLContext;
import org.jooq.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static net.feminaexlux.player.model.Tables.MUSIC;
import static net.feminaexlux.player.model.Tables.NORMALIZED_TEXT;
import static net.feminaexlux.player.model.Tables.RESOURCE;

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

		return database.select(ArrayUtils.addAll(MUSIC.fields())).from(RESOURCE)
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

	@Override
	public MusicResource find(final String checksum) {
		MusicRecord musicRecord = database.fetchOne(MUSIC, MUSIC.RESOURCE.equal(checksum));
		ResourceRecord resourceRecord = database.fetchOne(RESOURCE, RESOURCE.CHECKSUM.equal(checksum));
		return new MusicResource(musicRecord, resourceRecord);
	}

	public static class MusicResource {

		private final MusicRecord musicRecord;
		private final ResourceRecord resourceRecord;

		public MusicResource(final MusicRecord musicRecord, final ResourceRecord resourceRecord) {
			this.musicRecord = musicRecord;
			this.resourceRecord = resourceRecord;
		}

		public MusicRecord getMusicRecord() {
			return musicRecord;
		}

		public ResourceRecord getResourceRecord() {
			return resourceRecord;
		}

		public String getFullFilePath() {
			return resourceRecord.getDirectory() + resourceRecord.getName();
		}
	}

}
