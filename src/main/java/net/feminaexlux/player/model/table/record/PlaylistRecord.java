/**
 * This class is generated by jOOQ
 */
package net.feminaexlux.player.model.table.record;

import net.feminaexlux.player.model.table.Playlist;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
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
public class PlaylistRecord extends UpdatableRecordImpl<PlaylistRecord> implements Record2<String, String> {

	private static final long serialVersionUID = -887452463;

	/**
	 * Setter for <code>media.playlist.name</code>.
	 */
	public void setName(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>media.playlist.name</code>.
	 */
	public String getName() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>media.playlist.user</code>.
	 */
	public void setUser(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>media.playlist.user</code>.
	 */
	public String getUser() {
		return (String) getValue(1);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record2<String, String> key() {
		return (Record2) super.key();
	}

	// -------------------------------------------------------------------------
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<String, String> fieldsRow() {
		return (Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<String, String> valuesRow() {
		return (Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field1() {
		return Playlist.PLAYLIST.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Playlist.PLAYLIST.USER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getUser();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlaylistRecord value1(String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlaylistRecord value2(String value) {
		setUser(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlaylistRecord values(String value1, String value2) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached PlaylistRecord
	 */
	public PlaylistRecord() {
		super(Playlist.PLAYLIST);
	}

	/**
	 * Create a detached, initialised PlaylistRecord
	 */
	public PlaylistRecord(String name, String user) {
		super(Playlist.PLAYLIST);

		setValue(0, name);
		setValue(1, user);
	}
}