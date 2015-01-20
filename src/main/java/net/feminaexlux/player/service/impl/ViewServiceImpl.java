package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.page.MusicItem;
import net.feminaexlux.player.service.ViewService;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.TableField;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static net.feminaexlux.player.model.Tables.MUSIC;

public class ViewServiceImpl implements ViewService {

	@Override
	public List<MusicItem> toMusicItems(final Result<Record> result) {
		if (CollectionUtils.isEmpty(result)) {
			return Collections.emptyList();
		}

		return result.stream().map(record ->
				new MusicItem.Builder()
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

	@Override
	public <TYPE, TABLE_RECORD extends Record> List<String> toList(Result<Record1<TYPE>> result, TableField<TABLE_RECORD, TYPE> field) {
		return result.stream().map(record -> record.getValue(field).toString()).collect(Collectors.toList());
	}
}
