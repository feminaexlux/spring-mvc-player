/**
 * This class is generated by jOOQ
 */
package net.feminaexlux.player.model.table.record;

import net.feminaexlux.player.model.table.Type;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Row1;
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
public class TypeRecord extends UpdatableRecordImpl<TypeRecord> implements Record1<String> {

	private static final long serialVersionUID = -1517243645;

	/**
	 * Setter for <code>media.type.type</code>.
	 */
	public void setType(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>media.type.type</code>.
	 */
	public String getType() {
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
		return Type.TYPE.TYPE_;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TypeRecord value1(String value) {
		setType(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TypeRecord values(String value1) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached TypeRecord
	 */
	public TypeRecord() {
		super(Type.TYPE);
	}

	/**
	 * Create a detached, initialised TypeRecord
	 */
	public TypeRecord(String type) {
		super(Type.TYPE);

		setValue(0, type);
	}
}
