package net.feminaexlux.player.service;

import net.feminaexlux.player.model.item.DirectoryItem;
import net.feminaexlux.player.model.item.KeyValue;
import net.feminaexlux.player.model.item.MusicItem;
import net.feminaexlux.player.model.table.record.DirectoryRecord;
import net.feminaexlux.player.model.table.record.MusicRecord;

import java.util.List;
import java.util.Map;

public interface ViewService {

	List<DirectoryItem> toDirectoryItems(final List<DirectoryRecord> directoryRecords);

	List<MusicItem> toMusicItems(final List<MusicRecord> musicRecords);

	<K, V> List<KeyValue<String, String>> toKeyValues(final Map<K, V> listItems);

}
