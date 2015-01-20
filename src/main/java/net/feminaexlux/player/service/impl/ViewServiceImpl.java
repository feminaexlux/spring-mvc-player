package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.page.MusicPage;
import net.feminaexlux.player.service.ViewService;
import org.jooq.Record;
import org.jooq.Result;

import java.util.List;
import java.util.stream.Collectors;

import static net.feminaexlux.player.model.Tables.MUSIC;

public class ViewServiceImpl implements ViewService {

	@Override
	public List<MusicPage> toMusicView(final Result<Record> result) {
		return result.stream().map(record ->
				new MusicPage.Builder()
						.resource(record.getValue(MUSIC.RESOURCE))
						.artist(record.getValue(MUSIC.ARTIST))
						.album(record.getValue(MUSIC.ALBUM))
						.title(record.getValue(MUSIC.TITLE))
						.genre(record.getValue(MUSIC.GENRE))
						.trackNumber(record.getValue(MUSIC.TRACK))
						.rating(record.getValue(MUSIC.RATING))
						.build())
				.collect(Collectors.toList());
	}
}
