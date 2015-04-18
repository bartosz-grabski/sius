package camel.bookshop;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jetty.JettyHttpComponent;
import org.apache.camel.component.jetty.JettyHttpEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BookshopMainApp {

	public static void main(String[] args) {

		System.out.println("============================ Starting");
		Logger logger = LoggerFactory.getLogger(BookshopMainApp.class);		
		logger.info("Logger ready");	
				
		try{
			
			// CONTEXT
			JndiContext jndiContext = new JndiContext();
			jndiContext.bind("csvNormalizer", new CsvNormalizer());
			jndiContext.bind("formatHelper", new FormatHelper());
			CamelContext context = new DefaultCamelContext(jndiContext);
					
			// ROUTES
			context.addRoutes(new BookshopRouteBuilder());
			
			// ACTIVEMQ (JMS)
			context.addComponent("activemq", 
			ActiveMQComponent.activeMQComponent("vm://localhost?broker.persistent=false"));
			
		
			// RUN & TEST
			ProducerTemplate template = context.createProducerTemplate();		
			context.start();			
			System.out.println("Context started \n\n");

			//template.sendBody("direct:start", TestOrders.DirectCSV1);
			template.sendBody("direct:start", TestOrders.DirectCSV2);
			//template.sendBody("direct:start", TestOrders.DirectXML);
			//template.sendBody("http://localhost:8080/bookshop/", TestOrders.HttpCSV1);
//			template.sendBody("http://localhost:8080/bookshop/", TestOrders.HttpCSV2);
//			template.sendBody("http://localhost:8080/bookshop/", TestOrders.HttpXML);
			
			
			Thread.sleep(1000);
			System.out.println("\n\nStopping context");
			context.stop();			
		}
		catch(Exception e){
			System.out.println("EXCEPTION IN MAIN APP " + e.getMessage());
		}
		System.out.println("============================ Finished");
	}
}
