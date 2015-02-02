package net.feminaexlux.player.controller.music;

import net.feminaexlux.player.model.table.record.MusicRecord;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/music/artists")
public class ArtistController extends AbstractMusicController {

	@RequestMapping(method = RequestMethod.GET)
	public String list(final Model model) {
		model.addAttribute("title", "Artists");
		model.addAttribute("items", musicService.findAllArtists());
		return "list";
	}

	@RequestMapping(value = "/{artist}", method = RequestMethod.GET)
	public String list(@PathVariable final String artist, final Model model) {
		List<MusicRecord> artistMusic = musicService.findByArtist(artist);
		model.addAttribute("artist", artist);
		model.addAttribute("items", viewService.toMusicItems(artistMusic));
		return "artist";
	}

}
