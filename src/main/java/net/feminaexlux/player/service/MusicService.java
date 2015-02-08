package net.feminaexlux.player.service;

import net.feminaexlux.player.model.container.MusicResource;
import net.feminaexlux.player.model.table.record.MusicRecord;

import java.util.List;

public interface MusicService {

	List<MusicRecord> search(final String query);

	List<MusicRecord> recentlyPlayed(final int max);

	List<String> findAllArtists();

	List<MusicRecord> findByArtist(final String artist);

	List<String> findAllAlbums();

	List<MusicRecord> findByAlbum(final String album);

	MusicResource find(final String checksum);

	List<MusicResource> findAll();

	void setPlayed(final String checksum);

	void update(final List<MusicRecord> musicRecords);

}
