package com.briup.bigdata.project.grms.step5;

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


public class MutiplyGoodMatrixAndUserVector extends Configured implements Tool {

    public static class MutiplyGoodMatrixAndUserVectorFirstMapper
            extends Mapper<LongWritable, Text,Text,Text>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            StringBuilder sb = new StringBuilder();
            String[] gm = value.toString().split("\t");
            sb.append("f").append(gm[1]);
            context.write(new Text(gm[0]),new Text(sb.toString()));
        }
    }

    public static class MutiplyGoodMatrixAndUserVectorSecondMapper
            extends Mapper<LongWritable,Text,Text,Text>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            StringBuilder sb = new StringBuilder();
            String[] uv = value.toString().split("\t");
            sb.append("s").append(uv[1]);
            context.write(new Text(uv[0]),new Text(sb.toString()));
        }
    }

    public static class MutiplyGoodMatrixAndUserVectorReducer
            extends Reducer<Text,Text,Text,Text>{
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            StringBuilder sb = new StringBuilder();
            String[] uv = null;
            String[] gm = null;
            for (Text value : values) {
                String str = value.toString();
                if('s'==str.charAt(0)){
                    uv = str.substring(1).split(",");
                }
                if('f'==str.charAt(0)){
                    gm = str.substring(1).split(",");
                }
            }
            for (String s : uv) {
                for (String f : gm) {
                    String[] uvs = s.split(":");
                    String[] fgm = f.split(":");
                    sb.append(uvs[0]).append(":").append(fgm[0]);
                    int m = Integer.parseInt(uvs[1])*Integer.parseInt(fgm[1]);
                    context.write(new Text(sb.toString()),new Text(m+""));
                    sb.delete(0,sb.length());
                }
            }
        }
    }
    public int run(String[] strings) throws Exception {
        Configuration conf = getConf();
        Path input1 = new Path(conf.get("in1"));
        Path input2 = new Path(conf.get("in2"));
        Path output = new Path(conf.get("out"));

        Job job = Job.getInstance(conf,"grms5");
        job.setJarByClass(this.getClass());

        MultipleInputs.addInputPath(job,input1,TextInputFormat.class,MutiplyGoodMatrixAndUserVectorFirstMapper.class);
        MultipleInputs.addInputPath(job,input2,TextInputFormat.class,MutiplyGoodMatrixAndUserVectorSecondMapper.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(MutiplyGoodMatrixAndUserVectorReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job,output);

        return job.waitForCompletion(true)?0:1;
    }

    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new MutiplyGoodMatrixAndUserVector(),args));
    }
}
