/**
 * This class is generated by jOOQ
 */
package net.feminaexlux.player.model.table.record;


import net.feminaexlux.player.model.table.PrincipalResourceAccess;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;

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
public class PrincipalResourceAccessRecord extends UpdatableRecordImpl<PrincipalResourceAccessRecord> implements Record3<String, String, Timestamp> {

	private static final long serialVersionUID = 1342165987;

	/**
	 * Setter for <code>media.principal_resource_access.user</code>.
	 */
	public void setUser(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>media.principal_resource_access.user</code>.
	 */
	public String getUser() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>media.principal_resource_access.resource</code>.
	 */
	public void setResource(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>media.principal_resource_access.resource</code>.
	 */
	public String getResource() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>media.principal_resource_access.lastAccessed</code>.
	 */
	public void setLastaccessed(Timestamp value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>media.principal_resource_access.lastAccessed</code>.
	 */
	public Timestamp getLastaccessed() {
		return (Timestamp) getValue(2);
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
	public Row3<String, String, Timestamp> fieldsRow() {
		return (Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<String, String, Timestamp> valuesRow() {
		return (Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field1() {
		return PrincipalResourceAccess.PRINCIPAL_RESOURCE_ACCESS.USER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return PrincipalResourceAccess.PRINCIPAL_RESOURCE_ACCESS.RESOURCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field3() {
		return PrincipalResourceAccess.PRINCIPAL_RESOURCE_ACCESS.LASTACCESSED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getUser();
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
	public Timestamp value3() {
		return getLastaccessed();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrincipalResourceAccessRecord value1(String value) {
		setUser(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrincipalResourceAccessRecord value2(String value) {
		setResource(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrincipalResourceAccessRecord value3(Timestamp value) {
		setLastaccessed(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrincipalResourceAccessRecord values(String value1, String value2, Timestamp value3) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached PrincipalResourceAccessRecord
	 */
	public PrincipalResourceAccessRecord() {
		super(PrincipalResourceAccess.PRINCIPAL_RESOURCE_ACCESS);
	}

	/**
	 * Create a detached, initialised PrincipalResourceAccessRecord
	 */
	public PrincipalResourceAccessRecord(String user, String resource, Timestamp lastaccessed) {
		super(PrincipalResourceAccess.PRINCIPAL_RESOURCE_ACCESS);

		setValue(0, user);
		setValue(1, resource);
		setValue(2, lastaccessed);
	}
}
