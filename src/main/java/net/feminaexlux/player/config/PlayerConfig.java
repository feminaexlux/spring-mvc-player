package net.feminaexlux.player.config;

import com.mysql.jdbc.Driver;
import net.feminaexlux.player.service.DirectoryScannerService;
import net.feminaexlux.player.service.MusicService;
import net.feminaexlux.player.service.ViewService;
import net.feminaexlux.player.service.impl.DirectoryScannerServiceImpl;
import net.feminaexlux.player.service.impl.MusicServiceImpl;
import net.feminaexlux.player.service.impl.ViewServiceImpl;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@EnableScheduling
public class PlayerConfig {

	private static String SQL_URL      = System.getProperty("player.sql.url", "jdbc:mysql://localhost:3306/media");
	private static String SQL_USERNAME = System.getProperty("player.sql.username", "media");
	private static String SQL_PASSWORD = System.getProperty("player.sql.password", "media");

	@Bean
	public DSLContext database() throws SQLException {
		DriverManager.registerDriver(new Driver());
		Connection connection = DriverManager.getConnection(SQL_URL, SQL_USERNAME, SQL_PASSWORD);
		return DSL.using(connection, SQLDialect.MYSQL);
	}

	@Bean
	public DirectoryScannerService directoryScannerService() {
		return new DirectoryScannerServiceImpl();
	}

	@Bean
	public MusicService musicService() {
		return new MusicServiceImpl();
	}

	@Bean
	public ViewService viewService() {
		return new ViewServiceImpl();
	}

	@Scheduled(initialDelay = 60 * 1000, fixedDelay = 60 * 1000)
	public void keepAlive() throws SQLException {
		database().query("select 1").execute();
	}

}
