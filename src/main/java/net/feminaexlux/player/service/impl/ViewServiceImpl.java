package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.item.DirectoryItem;
import net.feminaexlux.player.model.item.KeyValue;
import net.feminaexlux.player.model.item.MusicItem;
import net.feminaexlux.player.model.table.UserPlayed;
import net.feminaexlux.player.model.table.record.DirectoryRecord;
import net.feminaexlux.player.model.table.record.MusicRecord;
import net.feminaexlux.player.service.ViewService;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ViewServiceImpl implements ViewService {

	@Autowired
	private DSLContext database;

	@Override
	public List<DirectoryItem> toDirectoryItems(final List<DirectoryRecord> directoryRecords) {
		if (CollectionUtils.isEmpty(directoryRecords)) {
			return Collections.emptyList();
		}

		return directoryRecords.stream().map(record ->
				new DirectoryItem.Builder()
						.location(record.getDirectory())
						.type(record.getType())
						.build())
				.collect(Collectors.toList());
	}

	@Override
	public List<MusicItem> toMusicItems(final List<MusicRecord> musicRecords) {
		if (CollectionUtils.isEmpty(musicRecords)) {
			return Collections.emptyList();
		}

		database.select().from(UserPlayed.USER_PLAYED);

		return musicRecords.stream().map(record ->
				new MusicItem.Builder()
						.resource(record.getChecksum())
						.artist(record.getArtist())
						.artistUrl(record.getArtistUrl())
						.album(record.getAlbum())
						.albumUrl(record.getAlbumUrl())
						.title(record.getTitle())
						.genre(record.getGenre())
						.trackNumber((int) record.getTrackNumber())
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

}