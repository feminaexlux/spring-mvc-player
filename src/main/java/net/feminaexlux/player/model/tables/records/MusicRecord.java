/**
 * This class is generated by jOOQ
 */
package net.feminaexlux.player.model.tables.records;

import net.feminaexlux.player.model.tables.Music;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;

/**
 * This class is generated by jOOQ.
 */
@Generated(
		value = {
				"http://www.jooq.org",
				"jOOQ version:3.5.0"
		},
		comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class MusicRecord extends UpdatableRecordImpl<MusicRecord> implements Record7<String, String, String, Integer, String, String, Byte> {

	private static final long serialVersionUID = -366299170;

	/**
	 * Setter for <code>media.music.resource</code>.
	 */
	public void setResource(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>media.music.resource</code>.
	 */
	public String getResource() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>media.music.artist</code>.
	 */
	public void setArtist(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>media.music.artist</code>.
	 */
	public String getArtist() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>media.music.album</code>.
	 */
	public void setAlbum(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>media.music.album</code>.
	 */
	public String getAlbum() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>media.music.track</code>.
	 */
	public void setTrack(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>media.music.track</code>.
	 */
	public Integer getTrack() {
		return (Integer) getValue(3);
	}

	/**
	 * Setter for <code>media.music.title</code>.
	 */
	public void setTitle(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>media.music.title</code>.
	 */
	public String getTitle() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>media.music.genre</code>.
	 */
	public void setGenre(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>media.music.genre</code>.
	 */
	public String getGenre() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>media.music.rating</code>.
	 */
	public void setRating(Byte value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>media.music.rating</code>.
	 */
	public Byte getRating() {
		return (Byte) getValue(6);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<String> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record7 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row7<String, String, String, Integer, String, String, Byte> fieldsRow() {
		return (Row7) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row7<String, String, String, Integer, String, String, Byte> valuesRow() {
		return (Row7) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<String> field1() {
		return Music.MUSIC.RESOURCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<String> field2() {
		return Music.MUSIC.ARTIST;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<String> field3() {
		return Music.MUSIC.ALBUM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<Integer> field4() {
		return Music.MUSIC.TRACK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<String> field5() {
		return Music.MUSIC.TITLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<String> field6() {
		return Music.MUSIC.GENRE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<Byte> field7() {
		return Music.MUSIC.RATING;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getResource();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getArtist();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getAlbum();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value4() {
		return getTrack();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getTitle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getGenre();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value7() {
		return getRating();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MusicRecord value1(String value) {
		setResource(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MusicRecord value2(String value) {
		setArtist(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MusicRecord value3(String value) {
		setAlbum(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MusicRecord value4(Integer value) {
		setTrack(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MusicRecord value5(String value) {
		setTitle(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MusicRecord value6(String value) {
		setGenre(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MusicRecord value7(Byte value) {
		setRating(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MusicRecord values(String value1, String value2, String value3, Integer value4, String value5, String value6, Byte value7) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached MusicRecord
	 */
	public MusicRecord() {
		super(Music.MUSIC);
	}

	/**
	 * Create a detached, initialised MusicRecord
	 */
	public MusicRecord(String resource, String artist, String album, Integer track, String title, String genre, Byte rating) {
		super(Music.MUSIC);

		setValue(0, resource);
		setValue(1, artist);
		setValue(2, album);
		setValue(3, track);
		setValue(4, title);
		setValue(5, genre);
		setValue(6, rating);
	}
}
