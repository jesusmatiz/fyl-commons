package co.com.ingeneo.utils.serverconfig;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

/**
 * Utility class to get the configuration from server.
 * 
 * @author cholguin
 * 
 */
public class ServerConfigUtils {

	/**
	 * Name of file configuration.
	 */
	private static String CONFIGURATION_FILE = "server-config.xml";

	/**
	 * Static instance.
	 */
	private static ServerConfigUtils serverConfigUtils = null;

	/**
	 * Server configuration.
	 */
	private ServerConfig serverConfig = null;

	/**
	 * Private constructor.
	 */
	private ServerConfigUtils() {

	}

	/**
	 * Method to get an instance using singleton pattern.
	 * 
	 * @return Instance.
	 */
	public static ServerConfigUtils getInstance() {
		if (serverConfigUtils == null) {
			serverConfigUtils = new ServerConfigUtils();
			serverConfigUtils.serverConfig = readConfiguration();
		}

		return serverConfigUtils;
	}
	
	/**
	 * Gets the JNDI name for Queue.
	 * @param key Alias to identity the resource
	 * @return JND name.
	 */
	public String getJNDIQueue(String key) {
		if (this.serverConfig  == null) {
			return "";
		}
		
		return this.serverConfig.getQueues().get(key);
	}
	
	/**
	 * Gets the JNDI name for Queue.
	 * @param key Alias to identity the resource
	 * @return JND name.
	 */
	public List<String> getAllJNDIQueue() {		
		List<String> queueList = new ArrayList<String>(this.serverConfig.getQueues().values());		
		return queueList;
	}
	
	/**
	 * Gets the JNDI name for local EJB.
	 * @param key Alias to identity the resource
	 * @return JND name.
	 */
	public String getJNDILocalEJB(String key) {
		if (this.serverConfig  == null) {
			return "";
		}
		
		return this.serverConfig.getLocalEJB().get(key);
	}
	
	/**
	 * Gets the JNDI name for remote EJB.
	 * @param key Alias to identity the resource
	 * @return JND name.
	 */
	public String getJNDIRmoteEJB(String key) {
		if (this.serverConfig  == null) {
			return "";
		}
		
		return this.serverConfig.getRemoteEJB().get(key);
	}
	
	

	/**
	 * Reads the configuration from file.
	 * @return ServerConfig
	 */
	private static ServerConfig readConfiguration() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser;
		try {
			saxParser = factory.newSAXParser();
			ServerConfigReader reader = new ServerConfigReader();

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream stream = loader.getResourceAsStream(CONFIGURATION_FILE);

			saxParser.parse(stream, reader);
			return reader.getServerConfig();

		} catch (ParserConfigurationException e) {			
			e.printStackTrace();
		} catch (SAXException e) {		
			e.printStackTrace();

		} catch (Exception e) {		
			e.printStackTrace();
		}
		return null;

	}

}
