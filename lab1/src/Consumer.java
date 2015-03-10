import java.util.Properties;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Consumer {

    public static void main(String[] args){


        Context context = null;
        TopicConnection connection = null;

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
            TopicConnectionFactory factory =
                    (TopicConnectionFactory)context.lookup("ConnectionFactory");
            connection = factory.createTopicConnection();
            System.out.println("Connection OK");

            // session & queue
            TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = (Topic)context.lookup("topic1");
            System.out.println("Topic OK");

            // PRODUCER
            TopicSubscriber receiver = session.createDurableSubscriber(topic,"mySub");
            connection.start();
            System.out.println("Connection started");

            while(true){
                System.out.println("Waiting for message...");
                TextMessage message = (TextMessage) receiver.receive();         // blocking
                System.out.println("Received message: " + message.getText());
            }
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
}
