package net.feminaexlux.player.service;

import net.feminaexlux.player.model.tables.records.MusicRecord;
import net.feminaexlux.player.service.impl.MusicServiceImpl.MusicResource;

import java.util.List;

public interface MusicService {

	List<MusicRecord> search(final String query);

	List<MusicRecord> recentlyPlayed(final int max);

	List<String> findAllArtists();

	List<MusicRecord> findByArtist(final String artist);

	List<String> findAllAlbums();

	List<MusicRecord> findByAlbum(final String album);

	MusicResource find(final String checksum);

}
