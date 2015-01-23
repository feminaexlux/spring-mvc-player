package net.feminaexlux.player;

import ch.qos.logback.classic.ViewStatusMessagesServlet;
import javafx.embed.swing.JFXPanel;
import net.feminaexlux.player.config.PlayerConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	public static final Boolean DEBUG = Boolean.parseBoolean(System.getProperty("player.log.debug", "false"));

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);

		Logger.getLogger("org.jaudiotagger").setLevel(Level.SEVERE);
		Logger.getLogger("org.jooq").setLevel(Level.SEVERE);

		final CountDownLatch latch = new CountDownLatch(1);
		SwingUtilities.invokeLater(() -> {
			new JFXPanel(); // initializes JavaFX environment
			latch.countDown();
		});

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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
		return new Class<?>[]{
				Application.class
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
