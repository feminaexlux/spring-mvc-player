package net.feminaexlux.player.controller;

import net.feminaexlux.player.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {

	protected static final String LIST_VIEW = "list";
	protected static final String REDIRECT_BASE = "redirect:/";

	@Autowired
	protected ViewService viewService;

}
