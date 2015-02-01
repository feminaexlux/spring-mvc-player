/**
 * This class is generated by jOOQ
 */
package net.feminaexlux.player.model.tables;

import net.feminaexlux.player.model.Keys;
import net.feminaexlux.player.model.Media;
import net.feminaexlux.player.model.tables.records.MoodRecord;
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
public class Mood extends TableImpl<MoodRecord> {

	private static final long serialVersionUID = 1606826744;

	/**
	 * The reference instance of <code>media.mood</code>
	 */
	public static final Mood MOOD = new Mood();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<MoodRecord> getRecordType() {
		return MoodRecord.class;
	}

	/**
	 * The column <code>media.mood.name</code>.
	 */
	public final TableField<MoodRecord, String> NAME = createField("name", SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>media.mood.user</code>.
	 */
	public final TableField<MoodRecord, String> USER = createField("user", SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>media.mood.description</code>.
	 */
	public final TableField<MoodRecord, String> DESCRIPTION = createField("description", SQLDataType.CLOB.length(65535), this, "");

	/**
	 * Create a <code>media.mood</code> table reference
	 */
	public Mood() {
		this("mood", null);
	}

	/**
	 * Create an aliased <code>media.mood</code> table reference
	 */
	public Mood(String alias) {
		this(alias, Mood.MOOD);
	}

	private Mood(String alias, Table<MoodRecord> aliased) {
		this(alias, aliased, null);
	}

	private Mood(String alias, Table<MoodRecord> aliased, Field<?>[] parameters) {
		super(alias, Media.MEDIA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<MoodRecord> getPrimaryKey() {
		return Keys.KEY_MOOD_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<MoodRecord>> getKeys() {
		return Arrays.<UniqueKey<MoodRecord>>asList(Keys.KEY_MOOD_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<MoodRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<MoodRecord, ?>>asList(Keys.FK1_MOOD_PRINCIPAL);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Mood as(String alias) {
		return new Mood(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Mood rename(String name) {
		return new Mood(name, null);
	}
}
