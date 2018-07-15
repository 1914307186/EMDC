package com.briup.bigdata.project.grms.step7;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

public class DuplicateDataForResult extends Configured implements Tool{

    public static class DuplicateDataForResultFirstMapper extends Mapper<LongWritable,Text,Text,Text>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            StringBuilder sb = new StringBuilder();
            String[] ma = value.toString().split("\t");
            String ustr = ma[0].trim();
            String gstr = ma[1].trim();
            String flag = ma[2].trim();
            sb.append(ustr).append(":").append(gstr);
            context.write(new Text(sb.toString()),new Text("f"+flag));
        }
    }

    public static class DuplicateDataForResultSecondMapper extends Mapper<LongWritable,Text,Text,Text>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            StringBuilder sb = new StringBuilder();
            String[] str = value.toString().split("\t");
            String skey = str[0];
            String svalue = str[1];
            sb.append("s").append(svalue);
            context.write(new Text(skey),new Text(sb.toString()));
        }
    }

    public static class DuplicateDataForResultReducer extends Reducer<Text,Text,Text,Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            String f = null;
            String s = null;
            for (Text value : values) {
                String str = value.toString();
                if('s'==str.charAt(0)){
                    s = str.substring(1);
                }
                if('f'==str.charAt(0)){
                    f = str.substring(1);
                }
            }
            StringBuilder sb = new StringBuilder();
            if((s!=null)&&(f==null)){
                String[] skey = key.toString().split(":");
                String u = skey[0];
                String g = skey[1];
                sb.append(u).append("\t").append(g);
                context.write(new Text(sb.toString()),new Text(s));
            }
        }
    }

    public int run(String[] strings) throws Exception {
        Configuration conf = getConf();
        Path input1 = new Path(conf.get("in1"));
        Path input2 = new Path(conf.get("in2"));
        Path output = new Path(conf.get("out"));

        Job job = Job.getInstance(conf,"grms7");
        job.setJarByClass(this.getClass());

        MultipleInputs.addInputPath(job,input1,TextInputFormat.class,DuplicateDataForResultFirstMapper.class);
        MultipleInputs.addInputPath(job,input2,TextInputFormat.class,DuplicateDataForResultSecondMapper.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(DuplicateDataForResultReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job,output);

        return job.waitForCompletion(true)?0:1;
    }

    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new DuplicateDataForResult(),args));
    }

}
