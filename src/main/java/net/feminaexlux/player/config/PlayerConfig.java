package net.feminaexlux.player.config;

import net.feminaexlux.player.service.MediaService;
import net.feminaexlux.player.service.impl.MediaServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerConfig {

	@Bean
	public MediaService mediaService() {
		return new MediaServiceImpl();
	}

}
