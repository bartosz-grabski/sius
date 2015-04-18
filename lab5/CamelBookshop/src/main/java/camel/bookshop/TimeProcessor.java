package camel.bookshop;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class TimeProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		
		Order o = (Order) exchange.getIn().getBody();
		o.setTimeReceived(new Date().toString());
		
	}


}
