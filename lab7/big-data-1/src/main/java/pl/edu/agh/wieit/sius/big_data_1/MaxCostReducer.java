package pl.edu.agh.wieit.sius.big_data_1;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MaxCostReducer extends
		Reducer<Text, DoubleWritable, Text, DoubleWritable> {
	private Text currentPhone = new Text();
	private double currentCost = Double.MIN_VALUE;

	@Override
	protected void reduce(Text phoneNumber, Iterable<DoubleWritable> maxCosts,
			Context context) throws IOException, InterruptedException {
		Iterator<DoubleWritable> it = maxCosts.iterator();
		double cost = it.next().get();

		if (it.hasNext()) {
			throw new IllegalArgumentException(
					"There shouldn't ever be more than one value per key. For key=["
							+ phoneNumber + "] there are a few values.");
		}

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
