package net.feminaexlux.player.controller.music;

import net.feminaexlux.player.model.container.MusicResource;
import net.feminaexlux.player.model.table.record.MusicRecord;
import net.feminaexlux.player.service.StreamingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/music")
public class MusicController extends AbstractMusicController {

	private static final String MUSIC_VIEW = "music/music";
	private static final String SONG_VIEW = "music/song";

	@Autowired
	private StreamingService streamingService;

	@RequestMapping(method = RequestMethod.GET)
	public String recentlyPlayed(final Model model) {
		List<MusicRecord> recentlyPlayedResult = musicService.recentlyPlayed(10);
		model.addAttribute("recentlyPlayed", viewService.toMusicItems(recentlyPlayedResult));
		return MUSIC_VIEW;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(@RequestParam final String query,
	                     final Model model,
	                     final RedirectAttributes redirectAttributes) {
		if (StringUtils.isEmpty(query)) {
			redirectAttributes.addFlashAttribute("errorMessage", "Please enter a search term");
			return "redirect:/music/";
		}

		List<MusicRecord> searchResults = musicService.search(query);
		model.addAttribute("searchResults", viewService.toMusicItems(searchResults));
		return MUSIC_VIEW;
	}

	@RequestMapping(value = "/play/{checksum}.mp3", method = RequestMethod.GET)
	public void play(@PathVariable final String checksum,
	                 final HttpServletRequest httpServletRequest,
	                 final HttpServletResponse httpServletResponse) throws IOException {
		MusicResource musicResource = musicService.find(checksum);
		streamingService.setMusicHeadersOnResponse(musicResource, httpServletRequest, httpServletResponse);
		musicService.setPlayed(checksum);
	}

	@RequestMapping(value = "/image/{checksum}", method = RequestMethod.GET)
	public void image(@PathVariable final String checksum,
	                  final HttpServletRequest httpServletRequest,
	                  final HttpServletResponse httpServletResponse) throws Exception {
		MusicResource musicResource = musicService.find(checksum);
		streamingService.setImageHeadersOnResponse(musicResource, httpServletRequest, httpServletResponse);
	}

	@RequestMapping(value = "/{checksum}", method = RequestMethod.GET)
	public String song(@PathVariable final String checksum, final Model model) {
		MusicResource musicResource = musicService.find(checksum);
		model.addAttribute("title", musicResource.getMusicRecord().getTitle());
		model.addAttribute("artist", musicResource.getMusicRecord().getArtist());
		model.addAttribute("album", musicResource.getMusicRecord().getAlbum());
		model.addAttribute("checksum", checksum);
		return SONG_VIEW;
	}

}
