package net.feminaexlux.player.service;

import net.feminaexlux.player.type.MediaType;
import org.jooq.Record;
import org.jooq.Result;

public interface MediaService {

	Result<Record> search(final String query);

	Result<Record> recentlyPlayed(final MediaType type, final int max);

}
