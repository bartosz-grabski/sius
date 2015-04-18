package camel.bookshop;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;



//@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Order implements Serializable {

	@XmlAttribute
	private String source;
	
	@XmlAttribute
	private String name;
	
	@XmlAttribute
	private int amount;

	@XmlAttribute
	private String date;

	private String timeReceived;
	
	
	public String getTimeReceived() {
		return timeReceived;
	}
	public void setTimeReceived(String timeReceived) {
		this.timeReceived = timeReceived;
	}
	public Order(){
		
	}


	public Order(String source, String name, int amount, String date){
		this.source = source;
		this.name = name;
		this.amount = amount;
		this.date = date;			
	}
	
	
	public String getSource() {
		return source;
	}
	public void setSource(String info) {
		this.source = info;
	}
	
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	@Override
    public String toString() {
        return "Received: [" + timeReceived + "]   Source: [" + source + "]   Details: [" + name + ", " + amount + ", " + date + "]";
    }
}

