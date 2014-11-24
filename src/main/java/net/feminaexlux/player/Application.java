package net.feminaexlux.player;

import com.mysql.jdbc.Driver;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.ReflectiveLoadTimeWeaver;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;

@ComponentScan
@Configuration
@EnableJpaRepositories(Application.REPOSITORY)
@EnableTransactionManagement
@EnableWebMvc
public class Application extends WebMvcConfigurerAdapter {

	public static final String JDBC_PASSWORD = "media";
	public static final String JDBC_URL = "jdbc:mysql://192.168.0.108/media";
	public static final String JDBC_USER = "media";
	public static final String MODEL = "net.feminaexlux.player.model";
	public static final String REPOSITORY = "net.feminaexlux.player.repository";

	@Bean
	public ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/view/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine(final ServletContextTemplateResolver templateResolver) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		templateEngine.addDialect(new LayoutDialect());
		return templateEngine;
	}

	@Bean
	public ThymeleafViewResolver viewResolver(final SpringTemplateEngine templateEngine) {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine);
		return viewResolver;
	}

	@Bean
	public DataSource dataSource() throws SQLException {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(Driver.class);
		dataSource.setUsername(JDBC_USER);
		dataSource.setPassword(JDBC_PASSWORD);
		dataSource.setUrl(JDBC_URL);
		return dataSource;
	}

	@Bean
	public JpaTransactionManager transactionManager(final DataSource dataSource,
	                                                final EntityManagerFactory entityManagerFactory) throws SQLException {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setDataSource(dataSource);
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory(final DataSource dataSource) throws SQLException {
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();

		bean.setDataSource(dataSource);
		bean.setJpaVendorAdapter(new OpenJpaVendorAdapter());
		bean.setLoadTimeWeaver(new ReflectiveLoadTimeWeaver());

		bean.afterPropertiesSet();
		return bean.getObject();
	}

}
