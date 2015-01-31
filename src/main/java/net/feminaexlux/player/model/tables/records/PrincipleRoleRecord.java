/**
 * This class is generated by jOOQ
 */
package net.feminaexlux.player.model.tables.records;

import net.feminaexlux.player.model.tables.PrincipleRole;
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
public class PrincipleRoleRecord extends UpdatableRecordImpl<PrincipleRoleRecord> implements Record2<String, String> {

	private static final long serialVersionUID = 849839945;

	/**
	 * Setter for <code>media.principle_role.username</code>.
	 */
	public void setUsername(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>media.principle_role.username</code>.
	 */
	public String getUsername() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>media.principle_role.role</code>.
	 */
	public void setRole(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>media.principle_role.role</code>.
	 */
	public String getRole() {
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
		return PrincipleRole.PRINCIPLE_ROLE.USERNAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return PrincipleRole.PRINCIPLE_ROLE.ROLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getUsername();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getRole();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrincipleRoleRecord value1(String value) {
		setUsername(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrincipleRoleRecord value2(String value) {
		setRole(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrincipleRoleRecord values(String value1, String value2) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached PrincipleRoleRecord
	 */
	public PrincipleRoleRecord() {
		super(PrincipleRole.PRINCIPLE_ROLE);
	}

	/**
	 * Create a detached, initialised PrincipleRoleRecord
	 */
	public PrincipleRoleRecord(String username, String role) {
		super(PrincipleRole.PRINCIPLE_ROLE);

		setValue(0, username);
		setValue(1, role);
	}
}
