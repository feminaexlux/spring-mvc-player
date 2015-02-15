package net.feminaexlux.player.service;

import net.feminaexlux.player.model.container.MusicResource;
import net.feminaexlux.player.model.table.record.MusicRecord;

import java.util.List;
import java.util.Map;

public interface MusicService {

	List<MusicRecord> search(final String query);

	List<MusicRecord> recentlyPlayed(final int max);

	Map<String, String> findAllArtists();

	List<MusicRecord> findByArtistUrl(final String artist);

	Map<String, String> findAllAlbums();

	List<MusicRecord> findByAlbumUrl(final String album);

	MusicResource find(final String checksum);

	List<MusicResource> findAll();

	void setPlayed(final String checksum);

	void update(final List<MusicRecord> musicRecords);

}
