package pl.edu.agh.wieit.sius.big_data_1;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

public class MeanRoamingCostMapper extends
		Mapper<ImmutableBytesWritable, Result, Text, DoubleWritable> {
	private static final Logger LOGGER = Logger
			.getLogger(MeanRoamingCostMapper.class);
	private final static Text REUSABLE_TEXT = new Text();

	@Override
	protected void map(ImmutableBytesWritable key, Result value, Context context)
			throws IOException, InterruptedException {
		Boolean roaming = Bytes.toBoolean(value.getValue(Bytes.toBytes("callData"),
				Bytes.toBytes("inRoaming")));
		String srcPhone = Bytes.toString(value.getValue(Bytes.toBytes("phoneData"),
				Bytes.toBytes("srcPhone")));
		Double cost = Bytes.toDouble(value.getValue(Bytes.toBytes("callData"),
				Bytes.toBytes("cost")));
		if (roaming) {
			REUSABLE_TEXT.set(srcPhone);
			context.write(REUSABLE_TEXT, new DoubleWritable(cost));
		}
	}
}
