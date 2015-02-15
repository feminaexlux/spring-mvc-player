/**
 * This class is generated by jOOQ
 */
package net.feminaexlux.player.model.table;

import net.feminaexlux.player.model.Key;
import net.feminaexlux.player.model.Media;
import net.feminaexlux.player.model.table.record.UserPlayedRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

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
public class UserPlayed extends TableImpl<UserPlayedRecord> {

	private static final long serialVersionUID = -1456437082;

	/**
	 * The reference instance of <code>media.user_played</code>
	 */
	public static final UserPlayed USER_PLAYED = new UserPlayed();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<UserPlayedRecord> getRecordType() {
		return UserPlayedRecord.class;
	}

	/**
	 * The column <code>media.user_played.username</code>.
	 */
	public final TableField<UserPlayedRecord, String> USERNAME = createField("username", SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>media.user_played.resource</code>.
	 */
	public final TableField<UserPlayedRecord, String> RESOURCE = createField("resource", SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>media.user_played.last_played</code>.
	 */
	public final TableField<UserPlayedRecord, Timestamp> LAST_PLAYED = createField("last_played", SQLDataType.TIMESTAMP.nullable(false), this, "");

	/**
	 * The column <code>media.user_played.rating</code>.
	 */
	public final TableField<UserPlayedRecord, Byte> RATING = createField("rating", SQLDataType.TINYINT, this, "");

	/**
	 * Create a <code>media.user_played</code> table reference
	 */
	public UserPlayed() {
		this("user_played", null);
	}

	/**
	 * Create an aliased <code>media.user_played</code> table reference
	 */
	public UserPlayed(String alias) {
		this(alias, UserPlayed.USER_PLAYED);
	}

	private UserPlayed(String alias, Table<UserPlayedRecord> aliased) {
		this(alias, aliased, null);
	}

	private UserPlayed(String alias, Table<UserPlayedRecord> aliased, Field<?>[] parameters) {
		super(alias, Media.MEDIA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<UserPlayedRecord> getPrimaryKey() {
		return Key.KEY_USER_PLAYED_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<UserPlayedRecord>> getKeys() {
		return Arrays.<UniqueKey<UserPlayedRecord>>asList(Key.KEY_USER_PLAYED_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<UserPlayedRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<UserPlayedRecord, ?>>asList(Key.FK1_USER_PLAYED_USER, Key.FK2_USER_PLAYED_RESOURCE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserPlayed as(String alias) {
		return new UserPlayed(alias, this);
	}

	/**
	 * Rename this table
	 */
	public UserPlayed rename(String name) {
		return new UserPlayed(name, null);
	}
}
