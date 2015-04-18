package camel.bookshop;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class PrintProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		
		if (exchange.getIn().getBody() instanceof Order)
		{
			Order order = (Order)exchange.getIn().getBody();
			System.out.println("Normalized order: " + order);	
		}
		else
		{
			System.out.println("Raw order: " + exchange.getIn().getBody().toString());
		}			
	}
}
