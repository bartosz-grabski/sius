package pl.edu.agh.wieit.sius.big_data_1;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class MeanRoamingCostReducer extends
		Reducer<Text, DoubleWritable, Text, DoubleWritable> {
	@Override
	protected void reduce(Text phoneNumber, Iterable<DoubleWritable> costs,
			Context context) throws IOException, InterruptedException {
		int count = 0;
		double sum = 0;
		for (DoubleWritable cost : costs) {
			count++;
			sum += cost.get();
		}

		context.write(phoneNumber, new DoubleWritable(sum / count));
	}
}
