package net.feminaexlux.player.controller.music;

import net.feminaexlux.player.controller.AbstractController;
import net.feminaexlux.player.model.container.MusicResource;
import net.feminaexlux.player.service.StreamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping(value = "/music")
public class MusicController extends AbstractController {

	// TODO create AbstractMusicController, move musicService into that, update PlayerController

	@Autowired
	private StreamingService streamingService;

	@RequestMapping(value = "/play/{checksum}.mp3", method = RequestMethod.GET)
	public void play(@PathVariable final String checksum,
	                 final HttpServletRequest httpServletRequest,
	                 final HttpServletResponse httpServletResponse) throws IOException {
		MusicResource musicResource = musicService.find(checksum);
		streamingService.setHeadersOnResponse(musicResource, httpServletRequest, httpServletResponse);
	}

	@RequestMapping(value = "/{checksum}", method = RequestMethod.GET)
	public String song(@PathVariable final String checksum, final Model model) {
		MusicResource musicResource = musicService.find(checksum);
		model.addAttribute("title", musicResource.getMusicRecord().getTitle());
		model.addAttribute("artist", musicResource.getMusicRecord().getArtist());
		model.addAttribute("album", musicResource.getMusicRecord().getAlbum());
		model.addAttribute("checksum", checksum);
		return "song";
	}

}
