package net.feminaexlux.player.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (object == null || getClass() != object.getClass()) {
			return false;
		}

		if (!super.equals(object)) {
			return false;
		}

		Music that = (Music) object;

		return new EqualsBuilder()
				.appendSuper(true)
				.append(artist, that.artist)
				.append(album, that.album)
				.append(song, that.song)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(11, 31)
				.appendSuper(super.hashCode())
				.append(artist)
				.append(album)
				.append(song)
				.toHashCode();
	}
}
