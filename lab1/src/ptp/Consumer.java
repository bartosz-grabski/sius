package ptp;

import java.util.Properties;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Consumer {

    public static void main(String[] args){
    	
    	Context context = null;
    	QueueConnection connection = null;
    	
        try {            
            System.out.println("CONSUMER");
            
            // context
            Properties props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, 
                    "org.exolab.jms.jndi.InitialContextFactory");    
            props.put(Context.PROVIDER_URL, "tcp://localhost:3035/");
            context = new InitialContext(props);
            System.out.println("Context OK");
            
            // connection
            QueueConnectionFactory factory = 
                    (QueueConnectionFactory)context.lookup("ConnectionFactory");
            connection = factory.createQueueConnection();
            System.out.println("Connection OK");
            
            // session & queue
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE); 
            Queue queue = (Queue)context.lookup("queue1");             
            System.out.println("Queue OK");
            
            // CONSUMER
            QueueReceiver receiver = session.createReceiver(queue,"name = 'ala'");
            receiver.setMessageListener(new MyListener());

            System.out.println("Connection started");

            connection.start();

        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
         } finally {
			
            // close the context
            if (context != null) {
                try {
                    context.close();
                } catch (NamingException exception) {
                    exception.printStackTrace();
                }
            }

            // close the connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    private static class MyListener implements MessageListener {

        @Override
        public void onMessage(Message message) {
            try {
                TextMessage msg = (TextMessage) message;
                System.out.println("Received message "+msg.getText());
            } catch (Exception e ) {
                System.out.println(e.getMessage());
            }
        }
    }
}
