package camel.bookshop;


public class FormatHelper {
	
	public boolean isXml(String body){
		String[] split = body.split("\n");
		return split.length > 0 && split[0].length() > 1 && split[0].charAt(0) == '<' && split[0].charAt(split[0].length() - 1) == '>';
	}

	public boolean isCsv(String body) {
		String[] split = body.split("\n");
		return split.length > 0 && split[0].split(",").length > 0;
	}
	
}