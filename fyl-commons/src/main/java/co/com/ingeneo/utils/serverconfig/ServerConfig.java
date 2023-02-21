package co.com.ingeneo.utils.serverconfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Get the server config.
 * @author cholguin
 *
 */
public class ServerConfig {
	
	/**
	 * Queues name
	 */
	private Map<String, String> queues = new HashMap<String, String>();
	
	/**
	 * JNDI names for local EJB
	 */
	private Map<String, String> localEJB = new HashMap<String, String>();
	
	/**
	 * JNDI names for remote EJB.
	 */
	private Map<String, String> remoteEJB = new HashMap<String, String>();

	/**
	 * @return the queues
	 */
	public Map<String, String> getQueues() {
		return queues;
	}

	/**
	 * @param queues the queues to set
	 */
	public void setQueues(Map<String, String> queues) {
		this.queues = queues;
	}

	/**
	 * @return the remoteEJB
	 */
	public Map<String, String> getRemoteEJB() {
		return remoteEJB;
	}

	/**
	 * @param remoteEJB the remoteEJB to set
	 */
	public void setRemoteEJB(Map<String, String> remoteEJB) {
		this.remoteEJB = remoteEJB;
	}

	/**
	 * @return the localEJB
	 */
	public Map<String, String> getLocalEJB() {
		return localEJB;
	}

	/**
	 * @param localEJB the localEJB to set
	 */
	public void setLocalEJB(Map<String, String> localEJB) {
		this.localEJB = localEJB;
	}
	
	
	
	

}
