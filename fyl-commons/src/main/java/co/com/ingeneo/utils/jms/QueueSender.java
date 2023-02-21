package co.com.ingeneo.utils.jms;

import co.com.ingeneo.utils.jndi.ServiceLocator;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;

public class QueueSender
{
  private QueueConnectionFactory queueConnectionFactory;
  private Queue queue;
  private QueueConnection queueConnection;
  private QueueSession queueSession;
  private javax.jms.QueueSender queueSender;
  
  public QueueSender(String queueConnectionFactoryName, String queueName)
  {
    this.queueConnectionFactory = ((QueueConnectionFactory)ServiceLocator.lookup(QueueConnectionFactory.class, queueConnectionFactoryName));
    this.queue = ((Queue)ServiceLocator.lookup(Queue.class, queueName));
  }
  
  public QueueSender(QueueConnectionFactory queueConnectionFactory, Queue queue)
  {
    this.queueConnectionFactory = queueConnectionFactory;
    this.queue = queue;
  }
  
  public synchronized void connect()
    throws JMSException
  {
    if (this.queueConnection == null)
    {
      this.queueConnection = this.queueConnectionFactory.createQueueConnection();
      this.queueSession = this.queueConnection.createQueueSession(false, 1);
      this.queueSender = this.queueSession.createSender(this.queue);
    }
    else
    {
      //throw new JMSException("The QueueSender has been initialized before, disconnect it first.");
    }
  }
  
  public synchronized void disconnect()
    throws JMSException
  {
    if (this.queueConnection != null)
    {
      if (this.queueSender != null)
      {
        this.queueSender.close();
        this.queueSender = null;
      }
      if (this.queueSession != null)
      {
        this.queueSession.close();
        this.queueSession = null;
      }
      this.queueConnection.close();
      this.queueConnection = null;
    }
    else
    {
      throw new JMSException("The QueueSender has not been initialized, connect it first.");
    }
  }
  
  public ObjectMessage createObjectMessage()
    throws JMSException
  {
    return this.queueSession.createObjectMessage();
  }
  
  public void sendObjectMessage(ObjectMessage msg)
    throws JMSException
  {
    this.queueSender.send(msg);
  }
}
