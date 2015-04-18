package camel.bookshop;

public class TestOrders {

	public static String DirectCSV1 =
			"DirectCsv1, \"The Hitchhiker's Guide to the Galaxy\", \"1\", \"2011-01-01\"";
	
	public static String DirectCSV2 =
			"DirectCsv2, \"Journey to the Center of the Earth\", \"2\", \"2012-02-02\"\n" +					 
			"DirectCsv2, \"Brave New World\", \"2\", \"2012-02-02\"";
	
	public static String DirectXML =
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"<order source=\"DirectXML\" name=\"To Kill a Mockingbird\" amount=\"3\" date=\"2013-03-03\"/>";
	
	
	public static String HttpCSV1 =			
			"HttpCsv1, \"War and Peace\", \"1\", \"2011-01-01\"\n";
	
	public static String HttpCSV2 =
			"HttpCsv2, \"Crime and Punishment\", \"2\", \"2012-02-02\"\n" +
			"HttpCsv2, \"Pride and Prejudice \", \"2\", \"2012-02-02\"\n";
	
	public static String HttpXML =
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"<order source=\"HttpXML\" name=\"Romeo and Juliet\" amount=\"3\" date=\"2013-03-03\"/>\n";
}
