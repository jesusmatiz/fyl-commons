package co.com.ingeneo.utils.serverconfig;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * It reads the server configuration from the server-config.xml file. This file is used
 * to configure the JNDI names according to container rules.
 * 
 * @author cholguin
 * 
 */
public class ServerConfigReader extends DefaultHandler {

	/**
	 * Server Configuration
	 */
	private ServerConfig serverConfig = null;

	/**
	 * 
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase("server-config")) {
			this.serverConfig = new ServerConfig();
		}

		// Queues
		if (qName.equalsIgnoreCase("queues")) {
			Map<String, String> queues = new HashMap<String, String>();
			this.serverConfig.setQueues(queues);
		}

		if (qName.equalsIgnoreCase("queue")) {
			this.serverConfig.getQueues().put(attributes.getValue(0),
					attributes.getValue(1));
		}

		// Local EJB
		if (qName.equalsIgnoreCase("local-ejbs")) {
			Map<String, String> localEJB = new HashMap<String, String>();
			this.serverConfig.setLocalEJB(localEJB);
		}

		if (qName.equalsIgnoreCase("local-ejb")) {
			this.serverConfig.getLocalEJB().put(attributes.getValue(0),
					attributes.getValue(1));
		}

		// Remote EJB
		if (qName.equalsIgnoreCase("remote-ejbs")) {
			Map<String, String> remoteEJB = new HashMap<String, String>();
			this.serverConfig.setRemoteEJB(remoteEJB);
		}

		if (qName.equalsIgnoreCase("remote-ejb")) {
			this.serverConfig.getRemoteEJB().put(attributes.getValue(0),
					attributes.getValue(1));
		}
	}

	/**
	 * @return the serverConfig
	 */
	public ServerConfig getServerConfig() {
		return serverConfig;
	}
	

}
