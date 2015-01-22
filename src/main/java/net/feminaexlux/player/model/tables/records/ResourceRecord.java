/**
 * This class is generated by jOOQ
 */
package net.feminaexlux.player.model.tables.records;

import net.feminaexlux.player.model.tables.Resource;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
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
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ResourceRecord extends UpdatableRecordImpl<ResourceRecord> implements Record4<String, String, String, Timestamp> {

	private static final long serialVersionUID = -2107345468;

	/**
	 * Setter for <code>media.resource.checksum</code>.
	 */
	public void setChecksum(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>media.resource.checksum</code>.
	 */
	public String getChecksum() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>media.resource.directory</code>.
	 */
	public void setDirectory(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>media.resource.directory</code>.
	 */
	public String getDirectory() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>media.resource.name</code>.
	 */
	public void setName(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>media.resource.name</code>.
	 */
	public String getName() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>media.resource.lastAccessed</code>.
	 */
	public void setLastaccessed(Timestamp value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>media.resource.lastAccessed</code>.
	 */
	public Timestamp getLastaccessed() {
		return (Timestamp) getValue(3);
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
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<String, String, String, Timestamp> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<String, String, String, Timestamp> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field1() {
		return Resource.RESOURCE.CHECKSUM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Resource.RESOURCE.DIRECTORY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Resource.RESOURCE.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field4() {
		return Resource.RESOURCE.LASTACCESSED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getChecksum();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getDirectory();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value4() {
		return getLastaccessed();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceRecord value1(String value) {
		setChecksum(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceRecord value2(String value) {
		setDirectory(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceRecord value3(String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceRecord value4(Timestamp value) {
		setLastaccessed(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceRecord values(String value1, String value2, String value3, Timestamp value4) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ResourceRecord
	 */
	public ResourceRecord() {
		super(Resource.RESOURCE);
	}

	/**
	 * Create a detached, initialised ResourceRecord
	 */
	public ResourceRecord(String checksum, String directory, String name, Timestamp lastaccessed) {
		super(Resource.RESOURCE);

		setValue(0, checksum);
		setValue(1, directory);
		setValue(2, name);
		setValue(3, lastaccessed);
	}
}
