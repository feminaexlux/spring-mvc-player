package net.feminaexlux.player;

import net.feminaexlux.player.service.DirectoryScannerService;
import net.feminaexlux.player.service.MusicService;
import net.feminaexlux.player.service.StreamingService;
import net.feminaexlux.player.service.ViewService;
import net.feminaexlux.player.service.impl.DirectoryScannerServiceImpl;
import net.feminaexlux.player.service.impl.MusicServiceImpl;
import net.feminaexlux.player.service.impl.StreamingServiceImpl;
import net.feminaexlux.player.service.impl.ViewServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerConfig {

	@Bean
	public DirectoryScannerService directoryScannerService() {
		return new DirectoryScannerServiceImpl();
	}

	@Bean
	public MusicService musicService() {
		return new MusicServiceImpl();
	}

	@Bean
	public StreamingService streamingService() {
		return new StreamingServiceImpl();
	}

	@Bean
	public ViewService viewService() {
		return new ViewServiceImpl();
	}


}
