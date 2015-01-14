package net.feminaexlux.player.controller;

import net.feminaexlux.player.service.MediaService;
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
	public String index(final Model model) {
		return "player";
	}

}
