package net.feminaexlux.player.controller.music;

import net.feminaexlux.player.controller.AbstractController;
import net.feminaexlux.player.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractMusicController extends AbstractController {

	@Autowired
	protected MusicService musicService;

}
