package com.briup.bigdata.project.grms.step6;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

public class MakeSumForMultiplication extends Configured implements Tool {

    public static class MakeSumForMultiplicationMapper extends Mapper<LongWritable,Text,Text,Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] str = value.toString().split("\t");
            String rkey = str[0].trim();
            String rvalue = str[1].trim();
            context.write(new Text(rkey),new Text(rvalue));
        }
    }

    public static class MakeSumForMultiplicationReducer extends Reducer<Text,Text,Text,Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (Text value : values) {
                String str = value.toString();
                int v = Integer.parseInt(str);
                sum = sum+v;
            }
            context.write(key,new Text(sum+""));
        }
    }
    public int run(String[] strings) throws Exception {
        Configuration conf = getConf();

        Path input = new Path(conf.get("in"));
        Path output = new Path(conf.get("out"));

        Job job = Job.getInstance(conf,"grms6");

        job.setJarByClass(this.getClass());

        job.setMapperClass(MakeSumForMultiplicationMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job,input);

        job.setReducerClass(MakeSumForMultiplicationReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job,output);

        return job.waitForCompletion(true)?0:1;
    }

    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new MakeSumForMultiplication(),args));
    }
}
