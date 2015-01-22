package net.feminaexlux.player.controller;

import net.feminaexlux.player.model.tables.records.MusicRecord;
import net.feminaexlux.player.service.MusicService;
import net.feminaexlux.player.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/artists")
public class ArtistController {

	@Autowired
	private MusicService musicService;
	@Autowired
	private ViewService viewService;

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
