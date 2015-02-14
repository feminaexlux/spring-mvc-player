package net.feminaexlux.player.controller.music;

import net.feminaexlux.player.model.table.record.MusicRecord;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/music/albums")
public class AlbumController extends AbstractMusicController {

	private static final String ALBUM_VIEW = "album";

	@RequestMapping(method = RequestMethod.GET)
	public String list(final Model model) {
		model.addAttribute("title", "Albums");
		model.addAttribute("items", viewService.toKeyValues(musicService.findAllAlbums()));
		return LIST_VIEW;
	}

	@RequestMapping(value = "/{album}", method = RequestMethod.GET)
	public String list(@PathVariable final String album, final Model model) {
		List<MusicRecord> albumMusic = musicService.findByAlbum(album);
		model.addAttribute("album", CollectionUtils.isNotEmpty(albumMusic) ? albumMusic.get(0).getAlbum() : album);
		model.addAttribute("items", viewService.toMusicItems(albumMusic));
		return ALBUM_VIEW;
	}

}
