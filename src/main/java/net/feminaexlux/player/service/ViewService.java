package net.feminaexlux.player.service;

import net.feminaexlux.player.model.element.KeyValue;
import net.feminaexlux.player.model.element.MusicItem;
import net.feminaexlux.player.model.table.record.MusicRecord;

import java.util.List;
import java.util.Map;

public interface ViewService {

	List<MusicItem> toMusicItems(final List<MusicRecord> musicRecords);

	<K, V> List<KeyValue<String, String>> toKeyValues(final Map<K, V> listItems);

}
