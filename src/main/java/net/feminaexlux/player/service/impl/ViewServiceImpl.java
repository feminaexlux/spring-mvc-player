package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.element.MusicItem;
import net.feminaexlux.player.model.table.record.MusicRecord;
import net.feminaexlux.player.service.ViewService;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ViewServiceImpl implements ViewService {

	@Override
	public List<MusicItem> toMusicItems(final List<MusicRecord> musicRecords) {
		if (CollectionUtils.isEmpty(musicRecords)) {
			return Collections.emptyList();
		}

		return musicRecords.stream().map(record ->
				new MusicItem.Builder()
						.resource(record.getResource())
						.artist(record.getArtist())
						.album(record.getAlbum())
						.title(record.getTitle())
						.genre(record.getGenre())
						.trackNumber(record.getTrack())
						.rating(record.getRating())
						.build())
				.collect(Collectors.toList());
	}
}
