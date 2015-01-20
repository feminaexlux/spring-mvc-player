package net.feminaexlux.player.controller;

import net.feminaexlux.player.service.MusicService;
import net.feminaexlux.player.service.ViewService;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static net.feminaexlux.player.model.Tables.MUSIC;

@Controller
@RequestMapping(value = "/albums")
public class AlbumController {

	@Autowired
	private MusicService musicService;
	@Autowired
	private ViewService  viewService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(final Model model) {
		Result<Record1<String>> albums = musicService.findAllAlbums();
		model.addAttribute("title", "Albums");
		model.addAttribute("items", viewService.toList(albums, MUSIC.ALBUM));
		return "list";
	}

	@RequestMapping(value = "/{album}", method = RequestMethod.GET)
	public String list(@PathVariable final String album, final Model model) {
		Result<Record> albumMusic = musicService.findByAlbum(album);
		model.addAttribute("album", album);
		model.addAttribute("items", viewService.toMusicItems(albumMusic));
		return "album";
	}

}
