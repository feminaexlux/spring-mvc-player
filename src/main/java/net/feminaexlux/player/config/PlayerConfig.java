package net.feminaexlux.player.config;

import net.feminaexlux.player.service.DirectoryScanner;
import net.feminaexlux.player.service.MediaService;
import net.feminaexlux.player.service.impl.DirectoryScannerImpl;
import net.feminaexlux.player.service.impl.MediaServiceImpl;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class PlayerConfig {

	private static String SQL_URL      = System.getProperty("player.sql.url", "jdbc:mysql://localhost:3306/media");
	private static String SQL_USERNAME = System.getProperty("player.sql.username", "media");
	private static String SQL_PASSWORD = System.getProperty("player.sql.password", "media");

	@Bean
	public MediaService mediaService() {
		return new MediaServiceImpl();
	}

	@Bean
	public DirectoryScanner directoryScanner() {
		return new DirectoryScannerImpl();
	}

	@Bean
	public DSLContext database() throws SQLException {
		Connection connection = DriverManager.getConnection(SQL_URL, SQL_USERNAME, SQL_PASSWORD);
		return DSL.using(connection, SQLDialect.MYSQL);
	}

}
