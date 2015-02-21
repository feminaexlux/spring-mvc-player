package net.feminaexlux.player;

import net.feminaexlux.player.service.DirectoryService;
import net.feminaexlux.player.service.LibraryService;
import net.feminaexlux.player.service.MusicService;
import net.feminaexlux.player.service.StreamingService;
import net.feminaexlux.player.service.UserService;
import net.feminaexlux.player.service.ViewService;
import net.feminaexlux.player.service.impl.DirectoryServiceImpl;
import net.feminaexlux.player.service.impl.LibraryServiceImpl;
import net.feminaexlux.player.service.impl.MusicServiceImpl;
import net.feminaexlux.player.service.impl.StreamingServiceImpl;
import net.feminaexlux.player.service.impl.UserServiceImpl;
import net.feminaexlux.player.service.impl.ViewServiceImpl;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.sql.SQLException;

@Configuration
@EnableAsync
@EnableScheduling
public class PlayerConfig {

	private static final Logger LOG = LoggerFactory.getLogger(PlayerConfig.class);
	private static final int TWO_MINUTES = 2 * 60 * 1000;

	@Autowired
	private DSLContext database;

	@Bean
	public DirectoryService directoryService() {
		return new DirectoryServiceImpl();
	}

	@Bean
	public LibraryService libraryService() {
		return new LibraryServiceImpl();
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
	public UserService userService() {
		return new UserServiceImpl();
	}

	@Bean
	public ViewService viewService() {
		return new ViewServiceImpl();
	}

	@Scheduled(initialDelay = TWO_MINUTES, fixedDelay = TWO_MINUTES)
	public void keepAlive() throws SQLException {
		LOG.info("Sending keepalive query at {}", System.currentTimeMillis());
		database.query("select 1").execute();
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void updateDatabase() throws IOException {
		LibraryService libraryService = libraryService();
		libraryService.updateAllLibraries();
	}

}
