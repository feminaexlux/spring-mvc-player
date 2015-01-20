/**
 * This class is generated by jOOQ
 */
package net.feminaexlux.player.model.tables.records;

import net.feminaexlux.player.model.tables.MoodResource;
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
public class MoodResourceRecord extends UpdatableRecordImpl<MoodResourceRecord> implements Record2<String, String> {

	private static final long serialVersionUID = 425925595;

	/**
	 * Setter for <code>media.mood_resource.mood</code>.
	 */
	public void setMood(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>media.mood_resource.mood</code>.
	 */
	public String getMood() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>media.mood_resource.resource</code>.
	 */
	public void setResource(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>media.mood_resource.resource</code>.
	 */
	public String getResource() {
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
		return MoodResource.MOOD_RESOURCE.MOOD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return MoodResource.MOOD_RESOURCE.RESOURCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getMood();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getResource();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MoodResourceRecord value1(String value) {
		setMood(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MoodResourceRecord value2(String value) {
		setResource(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MoodResourceRecord values(String value1, String value2) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached MoodResourceRecord
	 */
	public MoodResourceRecord() {
		super(MoodResource.MOOD_RESOURCE);
	}

	/**
	 * Create a detached, initialised MoodResourceRecord
	 */
	public MoodResourceRecord(String mood, String resource) {
		super(MoodResource.MOOD_RESOURCE);

		setValue(0, mood);
		setValue(1, resource);
	}
}