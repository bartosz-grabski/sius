package pl.edu.agh.wieit.sius.big_data_1;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

public class MeanRoamingCostMapper extends
		Mapper<LongWritable, Text, Text, DoubleWritable> {
	private static final Logger LOGGER = Logger
			.getLogger(MeanRoamingCostMapper.class);
	private final static Text REUSABLE_TEXT = new Text();

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String[] splitRecord = value.toString().split(";");
		if (splitRecord.length == 12 && !splitRecord[1].isEmpty()
				&& !splitRecord[6].isEmpty()
				&& Boolean.parseBoolean(splitRecord[6])
				&& !splitRecord[8].isEmpty()) {
			try {
				REUSABLE_TEXT.set(splitRecord[1]);
				context.write(REUSABLE_TEXT,
						new DoubleWritable(Double.parseDouble(splitRecord[8])));
			} catch (NumberFormatException ex) {
				LOGGER.warn("Invalid record: " + value);
			}
		}
	}
}
