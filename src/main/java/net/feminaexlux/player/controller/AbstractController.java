package net.feminaexlux.player.controller;

import net.feminaexlux.player.service.MusicService;
import net.feminaexlux.player.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {

	@Autowired
	protected MusicService musicService;

	@Autowired
	protected ViewService viewService;

}
