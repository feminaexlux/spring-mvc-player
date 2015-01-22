/**
 * This class is generated by jOOQ
 */
package net.feminaexlux.player.model.tables;

import net.feminaexlux.player.model.Keys;
import net.feminaexlux.player.model.Media;
import net.feminaexlux.player.model.tables.records.NormalizedTextRecord;
import org.jooq.Field;
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
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class NormalizedText extends TableImpl<NormalizedTextRecord> {

	private static final long serialVersionUID = -551332260;

	/**
	 * The reference instance of <code>media.normalized_text</code>
	 */
	public static final NormalizedText NORMALIZED_TEXT = new NormalizedText();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<NormalizedTextRecord> getRecordType() {
		return NormalizedTextRecord.class;
	}

	/**
	 * The column <code>media.normalized_text.original</code>.
	 */
	public final TableField<NormalizedTextRecord, String> ORIGINAL = createField("original", SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>media.normalized_text.normalized</code>.
	 */
	public final TableField<NormalizedTextRecord, String> NORMALIZED = createField("normalized", SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * Create a <code>media.normalized_text</code> table reference
	 */
	public NormalizedText() {
		this("normalized_text", null);
	}

	/**
	 * Create an aliased <code>media.normalized_text</code> table reference
	 */
	public NormalizedText(String alias) {
		this(alias, NormalizedText.NORMALIZED_TEXT);
	}

	private NormalizedText(String alias, Table<NormalizedTextRecord> aliased) {
		this(alias, aliased, null);
	}

	private NormalizedText(String alias, Table<NormalizedTextRecord> aliased, Field<?>[] parameters) {
		super(alias, Media.MEDIA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<NormalizedTextRecord> getPrimaryKey() {
		return Keys.KEY_NORMALIZED_TEXT_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<NormalizedTextRecord>> getKeys() {
		return Arrays.<UniqueKey<NormalizedTextRecord>>asList(Keys.KEY_NORMALIZED_TEXT_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NormalizedText as(String alias) {
		return new NormalizedText(alias, this);
	}

	/**
	 * Rename this table
	 */
	public NormalizedText rename(String name) {
		return new NormalizedText(name, null);
	}
}
