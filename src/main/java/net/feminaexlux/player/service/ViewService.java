package net.feminaexlux.player.service;

import net.feminaexlux.player.view.MusicView;
import org.jooq.Record;
import org.jooq.Result;

import java.util.List;

public interface ViewService {

	List<MusicView> toMusicView(Result<Record> result);

}
