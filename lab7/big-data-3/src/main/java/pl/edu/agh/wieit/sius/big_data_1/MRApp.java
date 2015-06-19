package pl.edu.agh.wieit.sius.big_data_1;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

/**
 * Example Map Reduce application.
 * 
 * @author kornel
 */
public class MRApp extends Configured implements Tool {
	private static final Logger LOGGER = Logger.getLogger(MRApp.class);

	public static void main(String[] args) throws Exception {

		int exitCode = ToolRunner.run(new MRApp(), args);
		System.exit(exitCode);
	}

	public int run(String[] args) throws Exception {
		String tempDir = args[0];

		JobControl control = new JobControl("Worklfow-Example");
		ControlledJob step1 = new ControlledJob(getMeanRoamingCostJob(args[0],
				tempDir), null);

		control.addJob(step1);

		Thread workflowThread = new Thread(control, "Workflow-Thread");
		workflowThread.setDaemon(true);
		workflowThread.start();

		while (!control.allFinished())
			Thread.sleep(500);

		if (control.getFailedJobList().size() > 0) {
			LOGGER.error(control.getFailedJobList().size() + " jobs failed!");
			for (ControlledJob job : control.getFailedJobList()) {
				LOGGER.error(job.getJobName() + " failed");
			}
		} else
			LOGGER.info("Success!! Workflow completed ["
					+ control.getSuccessfulJobList().size() + "] jobs");

		return control.getFailedJobList().size() > 0 ? 1 : 0;
	}

	private Job getMeanRoamingCostJob(String inputDir, String tempDir)
			throws IOException {
		Job job = Job.getInstance(getConf(), "Maximum Roaming Call Cost");
		job.setJarByClass(getClass());

		// configure output and input source
		TextInputFormat.addInputPath(job, new Path(inputDir));
		job.setInputFormatClass(TableInputFormat.class);

		Configuration conf = job.getConfiguration();
		HBaseConfiguration.merge(conf, HBaseConfiguration.create(conf));

		TableMapReduceUtil.addDependencyJars(job);
		conf.set(TableInputFormat.INPUT_TABLE, "billings");
		conf.set(TableInputFormat.SCAN_COLUMNS,
				"phoneData:srcPhone callData:inRoaming callData:cost");

		// configure mapper and reducer
		job.setMapperClass(MeanRoamingCostMapper.class);
		job.setCombinerClass(MeanRoamingCostReducer.class);
		job.setReducerClass(MeanRoamingCostReducer.class);

		// configure output
		TextOutputFormat.setOutputPath(job, new Path(tempDir));
		job.setOutputFormatClass(TableOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleWritable.class);

		return job;
	}

	/**
	 * Removes the given directory from HDFS.
	 * 
	 * @param intermediatePath
	 *            a path of the directory to remove
	 * @throws IOException
	 */
	private void deleteIntermediateDir(Path intermediatePath)
			throws IOException {
		FileSystem fs = FileSystem.get(getConf());
		if (fs.exists(intermediatePath))
			fs.delete(intermediatePath, true);
	}
}
