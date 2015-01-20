package net.feminaexlux.player.view;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

import static net.feminaexlux.player.view.HashCodePrimes.MUSIC;

public class MusicView implements Serializable {

	private final String resource;
	private final String artist;
	private final String album;
	private final String title;
	private final String genre;
	private final int    trackNumber;
	private final int    rating;

	private MusicView(final Builder builder) {
		this.resource = builder.resource;
		this.artist = builder.artist;
		this.album = builder.album;
		this.title = builder.title;
		this.genre = builder.genre;
		this.trackNumber = builder.trackNumber;
		this.rating = builder.rating;
	}

	public String getResource() {
		return resource;
	}

	public String getArtist() {
		return artist;
	}

	public String getAlbum() {
		return album;
	}

	public String getTitle() {
		return title;
	}

	public String getGenre() {
		return genre;
	}

	public int getTrackNumber() {
		return trackNumber;
	}

	public int getRating() {
		return rating;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}

		if (object == this) {
			return true;
		}

		if (object.getClass() != getClass()) {
			return false;
		}

		MusicView that = (MusicView) object;
		return new EqualsBuilder()
				.append(this.artist, that.artist)
				.append(this.album, that.album)
				.append(this.title, that.title)
				.append(this.genre, that.genre)
				.append(this.trackNumber, that.trackNumber)
				.append(this.rating, that.rating)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(MUSIC.getPrime(), MUSIC.getMultiplier())
				.append(this.artist)
				.append(this.album)
				.append(this.title)
				.append(this.genre)
				.append(this.trackNumber)
				.append(this.rating)
				.toHashCode();
	}

	public static class Builder {

		private String resource;
		private String artist;
		private String album;
		private String title;
		private String genre;
		private int    trackNumber;
		private int    rating;

		public Builder resource(String resource) {
			this.resource = resource;
			return this;
		}

		public Builder artist(String artist) {
			this.artist = artist;
			return this;
		}

		public Builder album(String album) {
			this.album = album;
			return this;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder genre(String genre) {
			this.genre = genre;
			return this;
		}

		public Builder trackNumber(int trackNumber) {
			this.trackNumber = trackNumber;
			return this;
		}

		public Builder rating(int rating) {
			this.rating = rating;
			return this;
		}

		public MusicView build() {
			return new MusicView(this);
		}
	}

}
