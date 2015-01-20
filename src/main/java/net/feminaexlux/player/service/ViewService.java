package net.feminaexlux.player.service;

import net.feminaexlux.player.page.MusicPage;
import org.jooq.Record;
import org.jooq.Result;

import java.util.List;

public interface ViewService {

	List<MusicPage> toMusicView(Result<Record> result);

}
