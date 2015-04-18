package camel.bookshop;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

public class BookshopRouteBuilder extends RouteBuilder{
	
	@Override
	public void configure() {
		try{		
			getContext().setStreamCaching(true); 
			JaxbDataFormat jaxb = new JaxbDataFormat("camel.bookshop");			// src/main/resources -> jaxb.index
			
			TimeProcessor timeProcessor = new TimeProcessor();
			PrintProcessor printProcessor = new PrintProcessor();
			
			from("file:data/inbox?noop=true").to("file:data/out");	
			
			from("direct:start").choice()
				.when(method("formatHelper","isXml")).unmarshal(jaxb).to("activemq:orderQueue")
				.when(method("formatHelper","isCsv")).split(body().tokenize("\n"))
					.unmarshal().csv().to("bean:csvNormalizer?method=csvToOrder").to("activemq:orderQueue");
						
			from("activemq:orderQueue").process(timeProcessor).process(printProcessor).to("stream:out"); // "swap for xmpp later
			
			from("jetty:http://localhost:8080/bookshop/?disableReplyTo=true").to("stream:out");
			
			
			// --- normalize orders 
			// TODO: activemq:incomingQueue -> normalize -> activemq:orderQueue

			// --- process orders
			// TODO: activemq:orderQueue -> time processor, print processor, xmpp
			
		}
		catch(Exception e){
			System.out.println("EXCEPTION IN ROUTER " + e.getMessage());
		}
	}	

}
