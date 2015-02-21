package net.feminaexlux.player;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	public void onStartup(final ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);

		Logger.getLogger("org.jaudiotagger").setLevel(Level.WARNING);
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
