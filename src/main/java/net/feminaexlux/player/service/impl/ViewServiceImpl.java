package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.element.KeyValue;
import net.feminaexlux.player.model.element.MusicItem;
import net.feminaexlux.player.model.table.NormalizedText;
import net.feminaexlux.player.model.table.record.MusicRecord;
import net.feminaexlux.player.service.ViewService;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.DSLContext;
import org.jooq.Record3;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static net.feminaexlux.player.model.table.Music.MUSIC;
import static net.feminaexlux.player.model.table.NormalizedText.NORMALIZED_TEXT;

@Service
public class ViewServiceImpl implements ViewService {

	@Autowired
	private DSLContext database;

	@Override
	public List<MusicItem> toMusicItems(final List<MusicRecord> musicRecords) {
		if (CollectionUtils.isEmpty(musicRecords)) {
			return Collections.emptyList();
		}

		Map<String, ArtistAlbumUrl> urlFriendly = getUrlFriendlyArtistAlbum(musicRecords);

		return musicRecords.stream().map(record ->
				new MusicItem.Builder()
						.resource(record.getResource())
						.artist(record.getArtist())
						.artistUrl(urlFriendly.get(record.getResource()).getArtistUrl())
						.album(record.getAlbum())
						.albumUrl(urlFriendly.get(record.getResource()).getAlbumUrl())
						.title(record.getTitle())
						.genre(record.getGenre())
						.trackNumber(record.getTrack())
						.rating(record.getRating())
						.build())
				.collect(Collectors.toList());
	}

	@Override
	public <K, V> List<KeyValue<String, String>> toKeyValues(final Map<K, V> listItems) {
		List<KeyValue<String, String>> keyValues = listItems.entrySet().stream()
				.map(listItem -> new KeyValue.Builder<String, String>()
						.key(String.valueOf(listItem.getKey()))
						.value(String.valueOf(listItem.getValue()))
						.build())
				.collect(Collectors.toList());

		Collections.sort(keyValues);
		return keyValues;
	}

	private Map<String, ArtistAlbumUrl> getUrlFriendlyArtistAlbum(final List<MusicRecord> musicRecords) {
		NormalizedText artistNormalized = NORMALIZED_TEXT.as("artistNormalized");
		NormalizedText albumNormalized = NORMALIZED_TEXT.as("albumNormalized");
		List<String> resources = musicRecords.stream().map(musicRecord -> musicRecord.getResource()).collect(Collectors.toList());

		Result<Record3<String, String, String>> normalizedText =
				database.select(MUSIC.RESOURCE, artistNormalized.NORMALIZED.as("artistURL"), albumNormalized.NORMALIZED.as("albumURL"))
						.from(MUSIC)
						.leftOuterJoin(artistNormalized).on(MUSIC.ARTIST.equal(artistNormalized.ORIGINAL))
						.leftOuterJoin(albumNormalized).on(MUSIC.ALBUM.equal(albumNormalized.ORIGINAL))
						.where(MUSIC.RESOURCE.in(resources))
						.fetch();

		Map<String, ArtistAlbumUrl> urlFriendly = new HashMap<>();
		for (Record3<String, String, String> row : normalizedText) {
			urlFriendly.put(row.value1(), new ArtistAlbumUrl(row.value2(), row.value3()));
		}

		return urlFriendly;
	}

	private static final class ArtistAlbumUrl {

		private final String artistUrl;
		private final String albumUrl;

		public ArtistAlbumUrl(final String artistUrl, final String albumUrl) {
			this.artistUrl = artistUrl;
			this.albumUrl = albumUrl;
		}

		public String getArtistUrl() {
			return artistUrl;
		}

		public String getAlbumUrl() {
			return albumUrl;
		}
	}

}