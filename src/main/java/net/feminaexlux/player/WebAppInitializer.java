package net.feminaexlux.player;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	public static final Boolean DEBUG = Boolean.parseBoolean(System.getProperty("player.log.debug", "false"));

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);

		Logger.getLogger("org.jaudiotagger").setLevel(Level.SEVERE);
		Logger.getLogger("org.jooq").setLevel(Level.SEVERE);
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{
				SecurityConfig.class
		};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{
				ApplicationConfig.class
				, PlayerConfig.class
		};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{
				"/"
		};
	}
}
