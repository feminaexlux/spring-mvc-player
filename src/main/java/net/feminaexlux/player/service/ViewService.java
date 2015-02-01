package net.feminaexlux.player.service;

import net.feminaexlux.player.model.table.record.MusicRecord;
import net.feminaexlux.player.page.MusicItem;

import java.util.List;

public interface ViewService {

	List<MusicItem> toMusicItems(List<MusicRecord> musicRecords);

}
