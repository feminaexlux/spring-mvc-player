package net.feminaexlux.player;

import net.feminaexlux.player.config.PlayerConfig;
import net.feminaexlux.player.config.RepositoryConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{
				Application.class
				, PlayerConfig.class
				, RepositoryConfig.class
		};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{
				"/"
		};
	}
}
