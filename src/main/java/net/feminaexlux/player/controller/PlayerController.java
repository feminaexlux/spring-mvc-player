package net.feminaexlux.player.controller;

import net.feminaexlux.player.service.MediaService;
import net.feminaexlux.player.service.ViewService;
import net.feminaexlux.player.type.MediaType;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PlayerController {

	@Autowired
	private MediaService mediaService;
	@Autowired
	private ViewService  viewService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(final Model model) {
		Result<Record> recentlyPlayedResult = mediaService.recentlyPlayed(MediaType.MUSIC, 10);
		model.addAttribute("recentlyPlayed", viewService.toMusicView(recentlyPlayedResult));
		return "player";
	}

	@RequestMapping(value = "/scan", method = RequestMethod.GET)
	public String scan() {
		return "player";
	}

}
