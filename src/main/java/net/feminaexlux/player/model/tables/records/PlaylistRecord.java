/**
 * This class is generated by jOOQ
 */
package net.feminaexlux.player.model.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(
		value = {
				"http://www.jooq.org",
				"jOOQ version:3.5.0"
		},
		comments = "This class is generated by jOOQ"
)
@java.lang.SuppressWarnings({"all", "unchecked", "rawtypes"})
public class PlaylistRecord extends org.jooq.impl.UpdatableRecordImpl<net.feminaexlux.player.model.tables.records.PlaylistRecord> implements org.jooq.Record1<java.lang.String> {

	private static final long serialVersionUID = -1637742233;

	/**
	 * Setter for <code>media.playlist.name</code>.
	 */
	public void setName(java.lang.String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>media.playlist.name</code>.
	 */
	public java.lang.String getName() {
		return (java.lang.String) getValue(0);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.String> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record1 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row1<java.lang.String> fieldsRow() {
		return (org.jooq.Row1) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row1<java.lang.String> valuesRow() {
		return (org.jooq.Row1) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field1() {
		return net.feminaexlux.player.model.tables.Playlist.PLAYLIST.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value1() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlaylistRecord value1(java.lang.String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlaylistRecord values(java.lang.String value1) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached PlaylistRecord
	 */
	public PlaylistRecord() {
		super(net.feminaexlux.player.model.tables.Playlist.PLAYLIST);
	}

	/**
	 * Create a detached, initialised PlaylistRecord
	 */
	public PlaylistRecord(java.lang.String name) {
		super(net.feminaexlux.player.model.tables.Playlist.PLAYLIST);

		setValue(0, name);
	}
}
