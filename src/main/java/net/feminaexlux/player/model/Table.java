/**
 * This class is generated by jOOQ
 */
package net.feminaexlux.player.model;

import net.feminaexlux.player.model.table.Directory;
import net.feminaexlux.player.model.table.Mood;
import net.feminaexlux.player.model.table.MoodResource;
import net.feminaexlux.player.model.table.Music;
import net.feminaexlux.player.model.table.NormalizedText;
import net.feminaexlux.player.model.table.Playlist;
import net.feminaexlux.player.model.table.PlaylistEntry;
import net.feminaexlux.player.model.table.Principal;
import net.feminaexlux.player.model.table.PrincipalResourceAccess;
import net.feminaexlux.player.model.table.PrincipalRole;
import net.feminaexlux.player.model.table.Resource;
import net.feminaexlux.player.model.table.Type;
import net.feminaexlux.player.model.table.TypeExtension;

import javax.annotation.Generated;

/**
 * Convenience access to all tables in media
 */
@Generated(
		value = {
				"http://www.jooq.org",
				"jOOQ version:3.5.0"
		},
		comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Table {

	/**
	 * The table media.directory
	 */
	public static final Directory DIRECTORY = Directory.DIRECTORY;

	/**
	 * The table media.mood
	 */
	public static final Mood MOOD = Mood.MOOD;

	/**
	 * The table media.mood_resource
	 */
	public static final MoodResource MOOD_RESOURCE = MoodResource.MOOD_RESOURCE;

	/**
	 * The table media.music
	 */
	public static final Music MUSIC = Music.MUSIC;

	/**
	 * The table media.normalized_text
	 */
	public static final NormalizedText NORMALIZED_TEXT = NormalizedText.NORMALIZED_TEXT;

	/**
	 * The table media.playlist
	 */
	public static final Playlist PLAYLIST = Playlist.PLAYLIST;

	/**
	 * The table media.playlist_entry
	 */
	public static final PlaylistEntry PLAYLIST_ENTRY = PlaylistEntry.PLAYLIST_ENTRY;

	/**
	 * The table media.principal
	 */
	public static final Principal PRINCIPAL = Principal.PRINCIPAL;

	/**
	 * The table media.principal_role
	 */
	public static final PrincipalRole PRINCIPAL_ROLE = PrincipalRole.PRINCIPAL_ROLE;

	/**
	 * The table media.principal_resource_access
	 */
	public static final PrincipalResourceAccess PRINCIPAL_RESOURCE_ACCESS = PrincipalResourceAccess.PRINCIPAL_RESOURCE_ACCESS;

	/**
	 * The table media.resource
	 */
	public static final Resource RESOURCE = Resource.RESOURCE;

	/**
	 * The table media.type
	 */
	public static final Type TYPE = Type.TYPE;

	/**
	 * The table media.type_extension
	 */
	public static final TypeExtension TYPE_EXTENSION = TypeExtension.TYPE_EXTENSION;
}
