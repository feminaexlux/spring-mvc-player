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
@RequestMapping(value = "/music/artists")
public class ArtistController extends AbstractMusicController {

	private static final String ARTIST_VIEW = "music/artist";

	@RequestMapping(method = RequestMethod.GET)
	public String list(final Model model) {
		model.addAttribute("title", "Artists");
		model.addAttribute("items", viewService.toKeyValues(musicService.findAllArtists()));
		return LIST_VIEW;
	}

	@RequestMapping(value = "/{artist}", method = RequestMethod.GET)
	public String byArtist(@PathVariable final String artist, final Model model) {
		List<MusicRecord> artistMusic = musicService.findByArtistUrl(artist);
		model.addAttribute("artist", CollectionUtils.isNotEmpty(artistMusic) ? artistMusic.get(0).getArtist() : artist);
		model.addAttribute("items", viewService.toMusicItems(artistMusic));
		return ARTIST_VIEW;
	}

}
