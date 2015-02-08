package net.feminaexlux.player;

import net.feminaexlux.player.service.DirectoryScannerService;
import net.feminaexlux.player.service.MusicService;
import net.feminaexlux.player.service.StreamingService;
import net.feminaexlux.player.service.ViewService;
import net.feminaexlux.player.service.impl.DirectoryScannerServiceImpl;
import net.feminaexlux.player.service.impl.MusicServiceImpl;
import net.feminaexlux.player.service.impl.StreamingServiceImpl;
import net.feminaexlux.player.service.impl.ViewServiceImpl;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.sql.SQLException;

@Configuration
@EnableScheduling
public class PlayerConfig {

	private static final int TWO_MINUTES = 2 * 60 * 1000;

	@Autowired
	private DSLContext database;

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

	@Scheduled(initialDelay = TWO_MINUTES, fixedDelay = TWO_MINUTES)
	public void keepAlive() throws SQLException {
		database.query("select 1").execute();
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void updateDatabase() throws IOException {
		DirectoryScannerService directoryScannerService = directoryScannerService();
		directoryScannerService.updateAllLibraries();
	}

}
