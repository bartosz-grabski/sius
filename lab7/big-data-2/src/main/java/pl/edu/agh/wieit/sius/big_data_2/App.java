package pl.edu.agh.wieit.sius.big_data_2;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Example HBase application.
 * 
 * @author kornel
 */
public class App {
	public static void main(String[] args) throws IOException {
		Configuration conf = HBaseConfiguration.create();

		Logger.getRootLogger().setLevel(Level.WARN);

		HTable hTable = new HTable(conf, "billings");

		// TODO: put data from billing1.txt into the table

		hTable.close();

		hTable = new HTable(conf, "billings");

		// TODO: display an example record from the table

		hTable.close();
	}
}
