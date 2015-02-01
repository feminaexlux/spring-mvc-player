package net.feminaexlux.player.controller.music;

import net.feminaexlux.player.controller.AbstractController;
import net.feminaexlux.player.model.table.record.MusicRecord;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/albums")
public class AlbumController extends AbstractController {

	@RequestMapping(method = RequestMethod.GET)
	public String list(final Model model) {
		model.addAttribute("title", "Albums");
		model.addAttribute("items", musicService.findAllAlbums());
		return "list";
	}

	@RequestMapping(value = "/{album}", method = RequestMethod.GET)
	public String list(@PathVariable final String album, final Model model) {
		List<MusicRecord> albumMusic = musicService.findByAlbum(album);
		model.addAttribute("album", album);
		model.addAttribute("items", viewService.toMusicItems(albumMusic));
		return "album";
	}

}
