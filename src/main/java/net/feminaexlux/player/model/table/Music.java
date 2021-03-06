/**
 * This class is generated by jOOQ
 */
package net.feminaexlux.player.model.table;

import net.feminaexlux.player.model.Key;
import net.feminaexlux.player.model.Media;
import net.feminaexlux.player.model.table.record.MusicRecord;
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
public class Music extends TableImpl<MusicRecord> {

	private static final long serialVersionUID = -1796180221;

	/**
	 * The reference instance of <code>media.music</code>
	 */
	public static final Music MUSIC = new Music();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<MusicRecord> getRecordType() {
		return MusicRecord.class;
	}

	/**
	 * The column <code>media.music.checksum</code>.
	 */
	public final TableField<MusicRecord, String> CHECKSUM = createField("checksum", SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>media.music.artist</code>.
	 */
	public final TableField<MusicRecord, String> ARTIST = createField("artist", SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>media.music.artist_url</code>.
	 */
	public final TableField<MusicRecord, String> ARTIST_URL = createField("artist_url", SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>media.music.album</code>.
	 */
	public final TableField<MusicRecord, String> ALBUM = createField("album", SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>media.music.album_url</code>.
	 */
	public final TableField<MusicRecord, String> ALBUM_URL = createField("album_url", SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>media.music.track_number</code>.
	 */
	public final TableField<MusicRecord, Byte> TRACK_NUMBER = createField("track_number", SQLDataType.TINYINT.nullable(false), this, "");

	/**
	 * The column <code>media.music.title</code>.
	 */
	public final TableField<MusicRecord, String> TITLE = createField("title", SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>media.music.year</code>.
	 */
	public final TableField<MusicRecord, Short> YEAR = createField("year", SQLDataType.SMALLINT, this, "");

	/**
	 * The column <code>media.music.genre</code>.
	 */
	public final TableField<MusicRecord, String> GENRE = createField("genre", SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * Create a <code>media.music</code> table reference
	 */
	public Music() {
		this("music", null);
	}

	/**
	 * Create an aliased <code>media.music</code> table reference
	 */
	public Music(String alias) {
		this(alias, Music.MUSIC);
	}

	private Music(String alias, Table<MusicRecord> aliased) {
		this(alias, aliased, null);
	}

	private Music(String alias, Table<MusicRecord> aliased, Field<?>[] parameters) {
		super(alias, Media.MEDIA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<MusicRecord> getPrimaryKey() {
		return Key.KEY_MUSIC_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<MusicRecord>> getKeys() {
		return Arrays.<UniqueKey<MusicRecord>>asList(Key.KEY_MUSIC_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<MusicRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<MusicRecord, ?>>asList(Key.FK1_MUSIC_RESOURCE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Music as(String alias) {
		return new Music(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Music rename(String name) {
		return new Music(name, null);
	}
}
