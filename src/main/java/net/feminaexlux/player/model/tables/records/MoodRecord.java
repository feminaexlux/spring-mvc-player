/**
 * This class is generated by jOOQ
 */
package net.feminaexlux.player.model.tables.records;

import net.feminaexlux.player.model.tables.Mood;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Row1;
import org.jooq.impl.UpdatableRecordImpl;

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
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class MoodRecord extends UpdatableRecordImpl<MoodRecord> implements Record1<String> {

	private static final long serialVersionUID = -1064115099;

	/**
	 * Setter for <code>media.mood.name</code>.
	 */
	public void setName(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>media.mood.name</code>.
	 */
	public String getName() {
		return (String) getValue(0);
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
	// Record1 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row1<String> fieldsRow() {
		return (Row1) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row1<String> valuesRow() {
		return (Row1) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field1() {
		return Mood.MOOD.NAME;
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
	public MoodRecord value1(String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MoodRecord values(String value1) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached MoodRecord
	 */
	public MoodRecord() {
		super(Mood.MOOD);
	}

	/**
	 * Create a detached, initialised MoodRecord
	 */
	public MoodRecord(String name) {
		super(Mood.MOOD);

		setValue(0, name);
	}
}