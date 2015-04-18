package camel.bookshop;

import java.util.Date;
import java.util.List;

public class CsvNormalizer {
	public Order csvToOrder(List<List<String>> data){		
		
		Order o = new Order();
		for (List<String> l : data) {
			o.setSource(l.get(0));
			o.setName(l.get(1));
			o.setAmount(Integer.parseInt(l.get(2)));
			o.setDate(l.get(3));
		}
		return o;
	}
}
