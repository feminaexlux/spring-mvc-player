package net.feminaexlux.player;

import com.mysql.jdbc.Driver;
import net.feminaexlux.player.service.security.AuthenticationManagerImpl;
import net.feminaexlux.player.service.security.UserDetailsServiceImpl;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@EnableScheduling
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final int TWO_MINUTES = 2 * 60 * 1000;
	private static final String SQL_URL = System.getProperty("player.sql.url", "jdbc:mysql://localhost:3306/media");
	private static final String SQL_USERNAME = System.getProperty("player.sql.username", "media");
	private static final String SQL_PASSWORD = System.getProperty("player.sql.password", "media");

	@Bean
	public DSLContext database() throws SQLException {
		DriverManager.registerDriver(new Driver());
		Connection connection = DriverManager.getConnection(SQL_URL, SQL_USERNAME, SQL_PASSWORD);
		return DSL.using(connection, SQLDialect.MYSQL);
	}

	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/setting/**").hasRole("ADMIN")
				.anyRequest().authenticated()
				.and()
				.formLogin();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		return new AuthenticationManagerImpl();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Scheduled(initialDelay = TWO_MINUTES, fixedDelay = TWO_MINUTES)
	public void keepAlive() throws SQLException {
		database().query("select 1").execute();
	}

}