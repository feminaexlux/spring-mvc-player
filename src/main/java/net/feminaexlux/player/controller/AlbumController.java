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
@RequestMapping(value = "/albums")
public class AlbumController {

	@Autowired
	private MusicService musicService;
	@Autowired
	private ViewService viewService;

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
