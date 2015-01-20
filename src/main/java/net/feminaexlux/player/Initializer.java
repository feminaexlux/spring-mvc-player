package net.feminaexlux.player;

import ch.qos.logback.classic.ViewStatusMessagesServlet;
import net.feminaexlux.player.config.PlayerConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	public static Boolean DEBUG = Boolean.parseBoolean(System.getProperty("player.log.debug", "false"));

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);

		if (DEBUG) {
			ServletRegistration.Dynamic viewStatusMessages =
					servletContext.addServlet("viewStatusMessages", new ViewStatusMessagesServlet());
			viewStatusMessages.setLoadOnStartup(1);
			viewStatusMessages.addMapping("/viewStatusMessages");
		}
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {
				Application.class
				, PlayerConfig.class
		};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {
				"/"
		};
	}
}
