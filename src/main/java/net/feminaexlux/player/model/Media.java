/**
 * This class is generated by jOOQ
 */
package net.feminaexlux.player.model;

import net.feminaexlux.player.model.tables.Directory;
import net.feminaexlux.player.model.tables.Mood;
import net.feminaexlux.player.model.tables.MoodResource;
import net.feminaexlux.player.model.tables.Music;
import net.feminaexlux.player.model.tables.NormalizedText;
import net.feminaexlux.player.model.tables.Playlist;
import net.feminaexlux.player.model.tables.PlaylistEntry;
import net.feminaexlux.player.model.tables.Principal;
import net.feminaexlux.player.model.tables.PrincipalRole;
import net.feminaexlux.player.model.tables.Resource;
import net.feminaexlux.player.model.tables.Type;
import net.feminaexlux.player.model.tables.TypeExtension;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import javax.annotation.Generated;
import java.util.ArrayList;
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
public class Media extends SchemaImpl {

	private static final long serialVersionUID = -993278684;

	/**
	 * The reference instance of <code>media</code>
	 */
	public static final Media MEDIA = new Media();

	/**
	 * No further instances allowed
	 */
	private Media() {
		super("media");
	}

	@Override
	public final List<Table<?>> getTables() {
		List result = new ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final List<Table<?>> getTables0() {
		return Arrays.<Table<?>>asList(
				Directory.DIRECTORY,
				Mood.MOOD,
				MoodResource.MOOD_RESOURCE,
				Music.MUSIC,
				NormalizedText.NORMALIZED_TEXT,
				Playlist.PLAYLIST,
				PlaylistEntry.PLAYLIST_ENTRY,
				Principal.PRINCIPAL,
				PrincipalRole.PRINCIPAL_ROLE,
				Resource.RESOURCE,
				Type.TYPE,
				TypeExtension.TYPE_EXTENSION);
	}
}
