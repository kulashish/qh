package in.ac.iitb.cse.qh.util;

import in.ac.iitb.cse.qh.classifiers.ModifiedLogistic;
import in.ac.iitb.cse.qh.data.ModelParams;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class MapReduce {

	public static final String theFilename = "inputfileslevel2.txt";
	public static final String message = "Hello, world!\n";
	public static final String weightsPerModelFile = "trainData/weightsPerModel.txt";

	public static class LogRegMapper extends Mapper<Object, Text, Text, Text> {

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {

			DataSource dt = null;
			Instances data = null;
			try {
				dt = new DataSource(value.toString());
				data = dt.getDataSet();
				if (data.classIndex() == -1)
					data.setClassIndex(data.numAttributes() - 1);

				ModifiedLogistic ml = new ModifiedLogistic();
				ml.buildClassifier(data);
				double[] params = ml.getWparameters();
				String s = "";
				for (int i = 0; i < params.length; i++)
					s = s + params[i] + " ";
				context.write(new Text("params"), new Text(s));
			} catch (Exception e) {
			}
		}
	}

	public static class LogRegReducer extends Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {

			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(conf);
			FSDataOutputStream outModel = fs.create(new Path(weightsPerModelFile));
			
			String[] models = new String[3];
			int i = 0;
			context.write(new Text("params"), new Text("test1"));
			for (Text val : values) {
				context.write(key, val);
				models[i] = val.toString();
				outModel.writeBytes(models[i]+"\n");
				i++;
			}
			outModel.close();
			
			String s = "";

			Path filenamePath = new Path(theFilename);

			FSDataInputStream in = fs.open(filenamePath);
			String messageIn = "";// in.readLine();
			while ((messageIn = in.readLine()) != null) {
				String[] filenames = messageIn.split(" ");
				FeatureConverter fc = new FeatureConverter(models,
						filenames[0], filenames[1]);
				if (fs.exists(new Path(filenames[1])))
					fs.delete(new Path(filenames[1]));
				FSDataOutputStream out = fs.create(new Path(filenames[1]));
				try {

					s = fc.writefromArray(context, out);
					context.write(new Text("params"), new Text("test5"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					context.write(new Text("params"), new Text(e.toString()));
				}
				out.close();

			}
			in.close();

			// FeatureConverter fc = new FeatureConverter(models,
			// "trainData/trainset-2-exe-split4.arff", "output.arff");
			// context.write(new Text("params"), new Text("test4"));

			// try {
			// s = fc.writefromFiletoString(context);
			// context.write(new Text("params"), new Text("test7"));
			// } catch (Exception e) {
			// // TODO Auto-generated catch block
			// context.write(new Text("params"), new Text(e.toString()));
			// }

		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 2) {
			System.out.println("Usage: MapReduce <in> <out>");
			System.exit(2);
		}
		Job job = new Job(conf, "Map Reduce");
		job.setJarByClass(MapReduce.class);
		job.setMapperClass(LogRegMapper.class);
		// job.setCombinerClass(LogRegReducer1.class);

		job.setReducerClass(LogRegReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

		System.out.println("input-" + otherArgs[0]);
		job.waitForCompletion(true);
		// System.exit(job.waitForCompletion(true) ? 0 : 1);
		System.out.println("output-" + otherArgs[1]);
	}
}