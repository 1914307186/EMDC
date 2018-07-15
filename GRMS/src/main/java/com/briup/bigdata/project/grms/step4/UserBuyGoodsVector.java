package com.briup.bigdata.project.grms.step4;

import com.briup.bigdata.project.grms.step3.GoodCooccurrenceMatrix;
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

public class UserBuyGoodsVector extends Configured implements Tool{

    public static class UserBuyGoodsVectorMapper extends Mapper<LongWritable,Text,Text,Text>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            StringBuilder sb = new StringBuilder();
            String[] arr = value.toString().split("\t");
            sb.append(arr[0]).append(":").append(arr[2]);
            context.write(new Text(arr[1]),new Text(sb.toString()));
        }
    }

    public static class UserBuyGoodsVectorReducer extends Reducer<Text,Text,Text,Text>{
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            StringBuilder sb = new StringBuilder();
            for (Text value : values) {
                String s = value.toString();
                sb.append(s).append(",");
            }
            context.write(key,new Text(sb.substring(0,sb.length()-1)));
        }
    }


    public int run(String[] strings) throws Exception {
        Configuration conf = getConf();
        Path input = new Path(conf.get("in"));
        Path output = new Path(conf.get("out"));

        Job job = Job.getInstance(conf,"grms4");

        job.setJarByClass(this.getClass());

        job.setMapperClass(UserBuyGoodsVectorMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job,input);

        job.setReducerClass(UserBuyGoodsVectorReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job,output);

        return job.waitForCompletion(true)?0:1;
    }

    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new UserBuyGoodsVector(),args));
    }

}
