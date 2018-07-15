package com.briup.bigdata.project.grms.step8;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

public class SaveRecommendResultToDB extends Configured implements Tool {
    public static class SaveRecommendResultToDBMapper extends Mapper<LongWritable,Text,Text,Text>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] strs = value.toString().split("\t");
            context.write(new Text(strs[0]+"\t"+strs[1]),new Text(strs[2]));
        }
    }
    public static class SaveRecommendResultToDBReducer extends Reducer<Text,Text,ValueToDB,NullWritable>{
        private ValueToDB vb = new ValueToDB();
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            String[] skey = key.toString().split("\t");
            vb.setUid(skey[0]);
            vb.setGid(skey[1]);
            Iterator it = values.iterator();
            String str = it.next().toString();
            int val = Integer.parseInt(str);
            vb.setExp(val);
            context.write(vb,NullWritable.get());
        }
    }
    public int run(String[] strings) throws Exception {
        Configuration conf = getConf();

        Path input = new Path(conf.get("in"));
        Path output = new Path(conf.get("out"));

        Job job = Job.getInstance(conf,"grms8");
        job.setJarByClass(this.getClass());

        job.setMapperClass(SaveRecommendResultToDBMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job,input);

        job.setReducerClass(SaveRecommendResultToDBReducer.class);
        job.setOutputKeyClass(ValueToDB.class);
        job.setOutputValueClass(NullWritable.class);
        job.setOutputFormatClass(DBOutputFormat.class);
        Properties prop = new Properties();
        prop.load(this.getClass().getResourceAsStream("/db.properties"));
        DBConfiguration.configureDB(
                job.getConfiguration(),
                prop.getProperty("grms.driver"),
                prop.getProperty("grms.url"),
                prop.getProperty("grms.user"),
                prop.getProperty("grms.password"));
        DBOutputFormat.setOutput(job,prop.getProperty("grms.tblname"),"uid","gid","exp");
        return job.waitForCompletion(true)?0:1;
    }

    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new SaveRecommendResultToDB(),args));
    }
}
