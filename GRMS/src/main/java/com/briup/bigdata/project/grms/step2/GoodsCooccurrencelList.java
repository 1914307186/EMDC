package com.briup.bigdata.project.grms.step2;

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


public class GoodsCooccurrencelList extends Configured implements Tool {

    public static class GoodsCooccurrencelListMapper extends Mapper<LongWritable,Text,Text,Text>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String good = value.toString().split("\t")[1].trim();
            String[] goods = good.split(",");

            for(int i=0;i<goods.length;i++){
                String goodi = goods[i];
                for(int j=0;j<goods.length;j++){
                    String goodj = goods[j];
                    context.write(new Text(goodi),new Text(goodj));
                }
            }
        }
    }

    public static class GoodsCooccurrencelListReducer extends Reducer<Text,Text,Text,Text>{
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text value : values) {
                context.write(key,value);
            }
        }
    }
    public int run(String[] strings) throws Exception {
        Configuration conf = getConf();
        Path input = new Path(conf.get("in"));
        Path output = new Path(conf.get("out"));

        Job job = Job.getInstance(conf,"grms2");

        job.setJarByClass(this.getClass());

        job.setMapperClass(GoodsCooccurrencelListMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job,input);

        job.setReducerClass(GoodsCooccurrencelListReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job,output);

        return job.waitForCompletion(true)?0:1;
    }

    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new GoodsCooccurrencelList(),args));
    }
}
