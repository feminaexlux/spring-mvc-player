package net.feminaexlux.player.model.item;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

import static net.feminaexlux.player.model.item.HashCodePrimes.MUSIC;

public class MusicItem implements Serializable {

	private static final long serialVersionUID = 3121558031287702463L;
	private final String resource;
	private final String artist;
	private final String artistUrl;
	private final String album;
	private final String albumUrl;
	private final String title;
	private final String genre;
	private final Integer trackNumber;
	private final int rating;

	private MusicItem(final Builder builder) {
		this.resource = builder.resource;
		this.artist = builder.artist;
		this.artistUrl = builder.artistUrl;
		this.album = builder.album;
		this.albumUrl = builder.albumUrl;
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

	public String getArtistUrl() {
		return artistUrl;
	}

	public String getAlbum() {
		return album;
	}

	public String getAlbumUrl() {
		return albumUrl;
	}

	public String getTitle() {
		return title;
	}

	public String getGenre() {
		return genre;
	}

	public Integer getTrackNumber() {
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

		MusicItem that = (MusicItem) object;
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
		private String artistUrl;
		private String album;
		private String albumUrl;
		private String title;
		private String genre;
		private Integer trackNumber;
		private int rating;

		public Builder resource(String resource) {
			this.resource = resource;
			return this;
		}

		public Builder artist(String artist) {
			this.artist = artist;
			return this;
		}

		public Builder artistUrl(String artistUrl) {
			this.artistUrl = artistUrl;
			return this;
		}

		public Builder album(String album) {
			this.album = album;
			return this;
		}

		public Builder albumUrl(String albumUrl) {
			this.albumUrl = albumUrl;
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

		public Builder trackNumber(Integer trackNumber) {
			this.trackNumber = trackNumber;
			return this;
		}

		public Builder rating(int rating) {
			this.rating = rating;
			return this;
		}

		public MusicItem build() {
			return new MusicItem(this);
		}
	}

}
