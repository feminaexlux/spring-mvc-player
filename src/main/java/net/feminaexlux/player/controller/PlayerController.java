package net.feminaexlux.player.controller;

import net.feminaexlux.player.service.MediaService;
import net.feminaexlux.player.type.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PlayerController {

	@Autowired
	private MediaService mediaService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		// More proof of concept that the service is actually being wired in correctly
		model.addAttribute("music", mediaService.getAll(MediaType.MUSIC));
		return "player";
	}

}
