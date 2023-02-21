package co.com.ingeneo.utils.jndi;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EJBPropertiesLoader {

	private static Properties properties;
	private static EJBPropertiesLoader INSTANCE;

	private EJBPropertiesLoader() {
		properties = new Properties();
		loadProperties();
	}

	public static EJBPropertiesLoader getInstance() {
		synchronized (EJBPropertiesLoader.class) {
			if (INSTANCE == null) {
				INSTANCE = new EJBPropertiesLoader();
			}
		}
		return INSTANCE;
	}

	public static void loadProperties() {
		try {
			String propertyFile = System.getProperty("ejb.properties");
	        File file = new File(propertyFile);
			properties.load(new FileInputStream(file));
		} catch (Exception ex) {
			log.error("[Error][EJBPropertiesLoader][loadProperties]Error abriendo el archivo", ex);
		}
	}

	public String getProperty(String name) {
		return properties.getProperty(name);
	}

	public Properties getProperties() {
		return properties;
	}
}
