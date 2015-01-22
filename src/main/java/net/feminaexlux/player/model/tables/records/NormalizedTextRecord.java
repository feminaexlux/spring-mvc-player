/**
 * This class is generated by jOOQ
 */
package net.feminaexlux.player.model.tables.records;

import net.feminaexlux.player.model.tables.NormalizedText;
import org.jooq.Field;
import org.jooq.Record1;
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
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class NormalizedTextRecord extends UpdatableRecordImpl<NormalizedTextRecord> implements Record2<String, String> {

	private static final long serialVersionUID = -1166296076;

	/**
	 * Setter for <code>media.normalized_text.original</code>.
	 */
	public void setOriginal(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>media.normalized_text.original</code>.
	 */
	public String getOriginal() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>media.normalized_text.normalized</code>.
	 */
	public void setNormalized(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>media.normalized_text.normalized</code>.
	 */
	public String getNormalized() {
		return (String) getValue(1);
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
		return NormalizedText.NORMALIZED_TEXT.ORIGINAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return NormalizedText.NORMALIZED_TEXT.NORMALIZED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getOriginal();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getNormalized();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NormalizedTextRecord value1(String value) {
		setOriginal(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NormalizedTextRecord value2(String value) {
		setNormalized(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NormalizedTextRecord values(String value1, String value2) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached NormalizedTextRecord
	 */
	public NormalizedTextRecord() {
		super(NormalizedText.NORMALIZED_TEXT);
	}

	/**
	 * Create a detached, initialised NormalizedTextRecord
	 */
	public NormalizedTextRecord(String original, String normalized) {
		super(NormalizedText.NORMALIZED_TEXT);

		setValue(0, original);
		setValue(1, normalized);
	}
}
