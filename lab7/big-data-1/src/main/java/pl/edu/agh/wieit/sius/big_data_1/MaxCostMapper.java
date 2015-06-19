package pl.edu.agh.wieit.sius.big_data_1;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MaxCostMapper extends Mapper<Text, Text, Text, DoubleWritable> {
	private Text currentPhone = new Text();
	private double currentCost = Double.MIN_VALUE;

	@Override
	protected void map(Text phoneNumber, Text costAsString, Context context)
			throws IOException, InterruptedException {
		double cost = Double.parseDouble(costAsString.toString());
		if (cost > currentCost) {
			currentPhone.set(phoneNumber);
			currentCost = cost;
		}
	}

	@Override
	protected void cleanup(Context context) throws IOException,
			InterruptedException {
		if (currentCost > Double.MIN_VALUE)
			context.write(currentPhone, new DoubleWritable(currentCost));
	}
}
