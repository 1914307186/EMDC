package com.briup.bigdata.project.grms.step3;

import com.briup.bigdata.project.grms.step2.GoodsCooccurrencelList;
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
import org.apache.hadoop.util.ToolRunner;;import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GoodCooccurrenceMatrix extends Configured implements Tool {

    public static class GoodCooccurrenceMatrixMapper extends Mapper<LongWritable,Text,Text,Text>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] arr = value.toString().split("\t");
            String good1 = arr[0].trim();
            String good2 = arr[1].trim();
            context.write(new Text(good1),new Text(good2));
        }
    }

    public static class GoodCooccurrenceMatrixReducer extends Reducer<Text,Text,Text,Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            StringBuilder sb = new StringBuilder();
            Map<String,Integer> res = new HashMap<String,Integer>();
            for (Text value : values) {
                String mkey = value.toString();
                Integer mvalue = res.get(mkey);
                if(res.containsKey(mkey)){
                    Integer i = res.get(mkey);
                    res.put(mkey,i+1);
                }else {
                    res.put(mkey,1);
                }
            }
            Set<String> keyset = res.keySet();
            for (String s : keyset) {
                Integer i = res.get(s);
                sb.append(s).append(":").append(i).append(",");
            }
            context.write(key,new Text(sb.substring(0,sb.length()-1)));
        }
    }
    public int run(String[] strings) throws Exception {
        Configuration conf = getConf();
        Path input = new Path(conf.get("in"));
        Path output = new Path(conf.get("out"));

        Job job = Job.getInstance(conf,"grms3");

        job.setJarByClass(this.getClass());

        job.setMapperClass(GoodCooccurrenceMatrixMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job,input);

        job.setReducerClass(GoodCooccurrenceMatrixReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job,output);

        return job.waitForCompletion(true)?0:1;
    }

    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new GoodCooccurrenceMatrix(),args));
    }
}
