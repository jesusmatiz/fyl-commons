package co.com.ingeneo.utils.jms;

import co.com.ingeneo.utils.jndi.ServiceLocator;

import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;

public class QueueReceiver {
	
	private QueueConnectionFactory queueConnectionFactory;
	private Queue queue;
	private QueueConnection queueConnection;
	private QueueSession queueSession;
	private javax.jms.QueueReceiver queueReceiver;
	private QueueBrowser queueBrowser;

	public QueueReceiver(String queueConnectionFactoryName, String queueName) {
		this.queueConnectionFactory = ((QueueConnectionFactory) ServiceLocator.lookup(QueueConnectionFactory.class,
				queueConnectionFactoryName));
		this.queue = ((Queue) ServiceLocator.lookup(Queue.class, queueName));
	}

	public QueueReceiver(QueueConnectionFactory queueConnectionFactory, Queue queue) {
		this.queueConnectionFactory = queueConnectionFactory;
		this.queue = queue;
	}

	public synchronized void connect() throws JMSException {
		if (this.queueConnection == null) {
			this.queueConnection = this.queueConnectionFactory.createQueueConnection();
			this.queueSession = this.queueConnection.createQueueSession(false, 1);
			this.queueReceiver = this.queueSession.createReceiver(this.queue);
			this.queueBrowser = this.queueSession.createBrowser(this.queue);
		} else {
			throw new JMSException("The QueueSender has been initialized before, disconnect it first.");
		}
	}

	public synchronized void disconnect() throws JMSException {
		if (this.queueConnection != null) {
			if (this.queueReceiver != null) {
				this.queueReceiver.close();
				this.queueReceiver = null;
			}
			if (this.queueBrowser != null) {
				this.queueBrowser.close();
				this.queueBrowser = null;
			}
			if (this.queueSession != null) {
				this.queueSession.close();
				this.queueSession = null;
			}
			this.queueConnection.close();
			this.queueConnection = null;
		} else {
			throw new JMSException("The QueueSender has not been initialized, connect it first.");
		}
	}

	public ObjectMessage createObjectMessage() throws JMSException {
		return this.queueSession.createObjectMessage();
	}

	public Message receiveObjectMessage() throws JMSException {
		this.queueConnection.start();

		Message message = this.queueReceiver.receive();

		return message;
	}

	public int getSize() throws JMSException {
		int numMsgs = 0;

		this.queueConnection.start();

		Enumeration e = this.queueBrowser.getEnumeration();
		while (e.hasMoreElements()) {
			Message message = (Message) e.nextElement();
			numMsgs++;
		}
		return numMsgs;
	}
}
