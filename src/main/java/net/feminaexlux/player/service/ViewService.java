package net.feminaexlux.player.service;

import net.feminaexlux.player.page.MusicItem;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.TableField;

import java.util.List;

public interface ViewService {

	List<MusicItem> toMusicItems(Result<Record> result);

	<TYPE, TABLE_RECORD extends Record> List<String> toList(Result<Record1<TYPE>> result, TableField<TABLE_RECORD, TYPE> field);

}
