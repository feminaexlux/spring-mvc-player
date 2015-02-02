package net.feminaexlux.player.service;

import net.feminaexlux.player.model.element.MusicItem;
import net.feminaexlux.player.model.table.record.MusicRecord;

import java.util.List;

public interface ViewService {

	List<MusicItem> toMusicItems(List<MusicRecord> musicRecords);

}
