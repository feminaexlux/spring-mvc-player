package net.feminaexlux.player.config;

import com.mysql.jdbc.Driver;
import net.feminaexlux.player.Application;
import org.reflections.Reflections;
import org.springframework.instrument.classloading.ReflectiveLoadTimeWeaver;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;

import javax.persistence.Entity;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;
import java.util.Set;

public class PersistenceUnitManagerImpl implements PersistenceUnitManager {

	private final DataSource dataSource;

	public PersistenceUnitManagerImpl(final DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public PersistenceUnitInfo obtainDefaultPersistenceUnitInfo() throws IllegalStateException {
		return buildPersistenceUnitInfo("default");
	}

	@Override
	public PersistenceUnitInfo obtainPersistenceUnitInfo(final String persistenceUnitName) throws IllegalArgumentException, IllegalStateException {
		return buildPersistenceUnitInfo(persistenceUnitName);
	}

	private PersistenceUnitInfo buildPersistenceUnitInfo(final String name) {
		SpringPersistenceUnitInfo persistenceUnitInfo = new SpringPersistenceUnitInfo();
		persistenceUnitInfo.init(new ReflectiveLoadTimeWeaver());
		persistenceUnitInfo.setPersistenceUnitName(name);

		Reflections reflections = new Reflections(Application.MODEL);
		Set<Class<?>> entities = reflections.getTypesAnnotatedWith(Entity.class);

		for (Class<?> entityClass : entities) {
			persistenceUnitInfo.addManagedClassName(entityClass.getName());
		}

		persistenceUnitInfo.setNonJtaDataSource(dataSource);
		persistenceUnitInfo.addProperty("openjpa.ConnectionURL", Application.JDBC_URL);
		persistenceUnitInfo.addProperty("openjpa.ConnectionDriverName", Driver.class.getName());
		persistenceUnitInfo.addProperty("openjpa.ConnectionUserName", Application.JDBC_USER);
		persistenceUnitInfo.addProperty("openjpa.ConnectionPassword", Application.JDBC_PASSWORD);

		return persistenceUnitInfo;
	}
}
