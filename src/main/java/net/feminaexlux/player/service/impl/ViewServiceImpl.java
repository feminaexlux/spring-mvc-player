package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.service.ViewService;
import net.feminaexlux.player.view.MusicView;
import org.jooq.Record;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;

import static net.feminaexlux.player.service.impl.MediaServiceImpl.MUSIC;

public class ViewServiceImpl implements ViewService {

	@Override
	public List<MusicView> toMusicView(final Result<Record> result) {
		List<MusicView> musicViews = new ArrayList<>();
		for (Record record : result) {
			musicViews.add(new MusicView.Builder()
					.resource(record.getValue(MUSIC.RESOURCE))
					.artist(record.getValue(MUSIC.ARTIST))
					.album(record.getValue(MUSIC.ALBUM))
					.title(record.getValue(MUSIC.TITLE))
					.genre(record.getValue(MUSIC.GENRE))
					.trackNumber(record.getValue(MUSIC.TRACK))
					.rating(record.getValue(MUSIC.RATING))
					.build());
		}
		return musicViews;
	}
}
