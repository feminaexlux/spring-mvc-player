/**
 * This class is generated by jOOQ
 */
package net.feminaexlux.player.model.tables;

import net.feminaexlux.player.model.Keys;
import net.feminaexlux.player.model.Media;
import net.feminaexlux.player.model.tables.records.DirectoryRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;

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
public class Directory extends TableImpl<DirectoryRecord> {

	private static final long serialVersionUID = 1917587952;

	/**
	 * The reference instance of <code>media.directory</code>
	 */
	public static final Directory DIRECTORY = new Directory();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<DirectoryRecord> getRecordType() {
		return DirectoryRecord.class;
	}

	/**
	 * The column <code>media.directory.location</code>.
	 */
	public final TableField<DirectoryRecord, String> LOCATION = createField("location", SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>media.directory.type</code>.
	 */
	public final TableField<DirectoryRecord, String> TYPE = createField("type", SQLDataType.VARCHAR.length(50).nullable(false), this, "");

	/**
	 * The column <code>media.directory.lastScanned</code>.
	 */
	public final TableField<DirectoryRecord, java.sql.Timestamp> LASTSCANNED = createField("lastScanned", SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>media.directory</code> table reference
	 */
	public Directory() {
		this("directory", null);
	}

	/**
	 * Create an aliased <code>media.directory</code> table reference
	 */
	public Directory(String alias) {
		this(alias, Directory.DIRECTORY);
	}

	private Directory(String alias, Table<DirectoryRecord> aliased) {
		this(alias, aliased, null);
	}

	private Directory(String alias, Table<DirectoryRecord> aliased, Field<?>[] parameters) {
		super(alias, Media.MEDIA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<DirectoryRecord> getPrimaryKey() {
		return Keys.KEY_DIRECTORY_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<DirectoryRecord>> getKeys() {
		return Arrays.<UniqueKey<DirectoryRecord>>asList(Keys.KEY_DIRECTORY_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<DirectoryRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<DirectoryRecord, ?>>asList(Keys.FK1_DIRECTORY_TYPE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Directory as(String alias) {
		return new Directory(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Directory rename(String name) {
		return new Directory(name, null);
	}
}
