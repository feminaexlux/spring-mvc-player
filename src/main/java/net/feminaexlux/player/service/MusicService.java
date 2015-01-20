package net.feminaexlux.player.service;

import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;

public interface MusicService {

	Result<Record> search(final String query);

	Result<Record> recentlyPlayed(final int max);

	Result<Record1<String>> findAllArtists();

	Result<Record> findByArtist(final String artist);

	Result<Record1<String>> findAllAlbums();

	Result<Record> findByAlbum(final String album);

}
