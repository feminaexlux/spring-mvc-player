package net.feminaexlux.player.config;

import com.mysql.jdbc.Driver;
import org.apache.openjpa.persistence.EntityManagerFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@EnableJpaRepositories(RepositoryConfig.REPOSITORY_LOCATION)
@EnableSpringDataWebSupport
public class RepositoryConfig {

	public static final String REPOSITORY_LOCATION = "net.feminaexlux.player.model";

	private static final String JDBC_PASSWORD = "media";
	private static final String JDBC_URL = "jdbc:mysql://192.168.0.108/media";
	private static final String JDBC_USER = "media";

	@Bean
	public DataSource dataSource() throws SQLException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(Driver.class.getName());
		dataSource.setUsername(JDBC_USER);
		dataSource.setPassword(JDBC_PASSWORD);
		dataSource.setUrl(JDBC_URL);
		return dataSource;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		return new OpenJpaVendorAdapter();
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() throws SQLException {
		return Persistence.createEntityManagerFactory("media-emf");
	}

	@Bean
	public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) throws SQLException {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

}
