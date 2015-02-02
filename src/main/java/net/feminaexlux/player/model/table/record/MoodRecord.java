/**
 * This class is generated by jOOQ
 */
package net.feminaexlux.player.model.table.record;

import net.feminaexlux.player.model.table.Mood;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Row3;
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
public class MoodRecord extends UpdatableRecordImpl<MoodRecord> implements Record3<String, String, String> {

	private static final long serialVersionUID = -1410920810;

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

	/**
	 * Setter for <code>media.mood.user</code>.
	 */
	public void setUser(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>media.mood.user</code>.
	 */
	public String getUser() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>media.mood.description</code>.
	 */
	public void setDescription(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>media.mood.description</code>.
	 */
	public String getDescription() {
		return (String) getValue(2);
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
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<String, String, String> fieldsRow() {
		return (Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<String, String, String> valuesRow() {
		return (Row3) super.valuesRow();
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
	public Field<String> field2() {
		return Mood.MOOD.USER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Mood.MOOD.DESCRIPTION;
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
	public String value3() {
		return getDescription();
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
	public MoodRecord value2(String value) {
		setUser(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MoodRecord value3(String value) {
		setDescription(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MoodRecord values(String value1, String value2, String value3) {
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
	public MoodRecord(String name, String user, String description) {
		super(Mood.MOOD);

		setValue(0, name);
		setValue(1, user);
		setValue(2, description);
	}
}