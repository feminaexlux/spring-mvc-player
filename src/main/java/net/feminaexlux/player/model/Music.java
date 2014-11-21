package net.feminaexlux.player.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "music")
public class Music extends Media {

	private String artist;
	private String album;
	private String song;

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getSong() {
		return song;
	}

	public void setSong(String song) {
		this.song = song;
	}
}
