package com.briup.bigdata.project.grms.main;

import com.briup.bigdata.project.grms.step1.UserBuyGoodsList;
import com.briup.bigdata.project.grms.step2.GoodsCooccurrencelList;
import com.briup.bigdata.project.grms.step3.GoodCooccurrenceMatrix;
import com.briup.bigdata.project.grms.step4.UserBuyGoodsVector;
import com.briup.bigdata.project.grms.step5.MutiplyGoodMatrixAndUserVector;
import com.briup.bigdata.project.grms.step6.MakeSumForMultiplication;
import com.briup.bigdata.project.grms.step7.DuplicateDataForResult;
import com.briup.bigdata.project.grms.step8.SaveRecommendResultToDB;
import com.briup.bigdata.project.grms.step8.ValueToDB;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.util.Properties;

public class GoodRecommendationManagementSystemJobController extends Configured implements Tool {

    @Override
    public int run(String[] strings) throws Exception {
        Configuration conf = getConf();
        Path input = new Path(conf.get("in"));
        Path output1 = new Path(conf.get("out1"));
        Path output2 = new Path(conf.get("out2"));
        Path output3 = new Path(conf.get("out3"));
        Path output4 = new Path(conf.get("out4"));
        Path output5 = new Path(conf.get("out5"));
        Path output6 = new Path(conf.get("out6"));
        Path output7 = new Path(conf.get("out7"));

        //1.创建每一步的job对象
        Job job1 = Job.getInstance(conf,"grms");

        job1.setJarByClass(this.getClass());

        job1.setMapperClass(UserBuyGoodsList.UserBuyGoodsListMapper.class);
        job1.setMapOutputKeyClass(Text.class);
        job1.setMapOutputValueClass(Text.class);
        job1.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job1,input);

        job1.setReducerClass(UserBuyGoodsList.UserBuyGoodsListReducer.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(Text.class);
        job1.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job1,output1);


        //--------------------------------------------------

        Job job2 = Job.getInstance(conf,"grms2");

        job2.setJarByClass(this.getClass());

        job2.setMapperClass(GoodsCooccurrencelList.GoodsCooccurrencelListMapper.class);
        job2.setMapOutputKeyClass(Text.class);
        job2.setMapOutputValueClass(Text.class);
        job2.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job2,output1);

        job2.setReducerClass(GoodsCooccurrencelList.GoodsCooccurrencelListReducer.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(Text.class);
        job2.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job2,output2);

        //------------------------------------------------
        Job job3 = Job.getInstance(conf,"grms3");

        job3.setJarByClass(this.getClass());

        job3.setMapperClass(GoodCooccurrenceMatrix.GoodCooccurrenceMatrixMapper.class);
        job3.setMapOutputKeyClass(Text.class);
        job3.setMapOutputValueClass(Text.class);
        job3.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job3,output2);

        job3.setReducerClass(GoodCooccurrenceMatrix.GoodCooccurrenceMatrixReducer.class);
        job3.setOutputKeyClass(Text.class);
        job3.setOutputValueClass(Text.class);
        job3.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job3,output3);

        //----------------------------------------------
        Job job4 = Job.getInstance(conf,"grms4");

        job4.setJarByClass(this.getClass());

        job4.setMapperClass(UserBuyGoodsVector.UserBuyGoodsVectorMapper.class);
        job4.setMapOutputKeyClass(Text.class);
        job4.setMapOutputValueClass(Text.class);
        job4.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job4,input);

        job4.setReducerClass(UserBuyGoodsVector.UserBuyGoodsVectorReducer.class);
        job4.setOutputKeyClass(Text.class);
        job4.setOutputValueClass(Text.class);
        job4.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job4,output4);

        //-----------------------------------------------------
        Job job5 = Job.getInstance(conf,"grms5");
        job5.setJarByClass(this.getClass());

        MultipleInputs.addInputPath(job5,output3,TextInputFormat.class,MutiplyGoodMatrixAndUserVector.MutiplyGoodMatrixAndUserVectorFirstMapper.class);
        MultipleInputs.addInputPath(job5,output4,TextInputFormat.class,MutiplyGoodMatrixAndUserVector.MutiplyGoodMatrixAndUserVectorSecondMapper.class);

        job5.setMapOutputKeyClass(Text.class);
        job5.setMapOutputValueClass(Text.class);

        job5.setReducerClass(MutiplyGoodMatrixAndUserVector.MutiplyGoodMatrixAndUserVectorReducer.class);
        job5.setOutputKeyClass(Text.class);
        job5.setOutputValueClass(IntWritable.class);
        job5.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job5,output5);

        //-------------------------------------------------------
        Job job6 = Job.getInstance(conf,"grms6");

        job6.setJarByClass(this.getClass());

        job6.setMapperClass(MakeSumForMultiplication.MakeSumForMultiplicationMapper.class);
        job6.setMapOutputKeyClass(Text.class);
        job6.setMapOutputValueClass(Text.class);
        job6.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job6,output5);

        job6.setReducerClass(MakeSumForMultiplication.MakeSumForMultiplicationReducer.class);
        job6.setOutputKeyClass(Text.class);
        job6.setOutputValueClass(Text.class);
        job6.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job6,output6);

        //---------------------------------------------
        Job job7 = Job.getInstance(conf,"grms7");
        job7.setJarByClass(this.getClass());

        MultipleInputs.addInputPath(job7,input,TextInputFormat.class,DuplicateDataForResult.DuplicateDataForResultFirstMapper.class);
        MultipleInputs.addInputPath(job7,output6,TextInputFormat.class,DuplicateDataForResult.DuplicateDataForResultSecondMapper.class);

        job7.setMapOutputKeyClass(Text.class);
        job7.setMapOutputValueClass(Text.class);

        job7.setReducerClass(DuplicateDataForResult.DuplicateDataForResultReducer.class);
        job7.setOutputKeyClass(Text.class);
        job7.setOutputValueClass(IntWritable.class);
        job7.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job7,output7);

        //------------------------------------------------------
        Job job8 = Job.getInstance(conf,"grms8");
        job8.setJarByClass(this.getClass());

        job8.setMapperClass(SaveRecommendResultToDB.SaveRecommendResultToDBMapper.class);
        job8.setMapOutputKeyClass(Text.class);
        job8.setMapOutputValueClass(Text.class);
        job8.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job8,output7);

        job8.setReducerClass(SaveRecommendResultToDB.SaveRecommendResultToDBReducer.class);
        job8.setOutputKeyClass(ValueToDB.class);
        job8.setOutputValueClass(NullWritable.class);
        job8.setOutputFormatClass(DBOutputFormat.class);
        Properties prop = new Properties();
        prop.load(this.getClass().getResourceAsStream("/db.properties"));
        DBConfiguration.configureDB(
                job8.getConfiguration(),
                prop.getProperty("grms.driver"),
                prop.getProperty("grms.url"),
                prop.getProperty("grms.user"),
                prop.getProperty("grms.password"));
        DBOutputFormat.setOutput(job8,prop.getProperty("grms.tblname"),"uid","gid","exp");

        //2.创建8个ControlledJob对象
        ControlledJob cj1 = new ControlledJob(job1.getConfiguration());
        cj1.setJob(job1);

        ControlledJob cj2 = new ControlledJob(job2.getConfiguration());
        cj2.setJob(job2);

        ControlledJob cj3 = new ControlledJob(job3.getConfiguration());
        cj3.setJob(job3);

        ControlledJob cj4 = new ControlledJob(job4.getConfiguration());
        cj4.setJob(job4);

        ControlledJob cj5 = new ControlledJob(job5.getConfiguration());
        cj5.setJob(job5);

        ControlledJob cj6 = new ControlledJob(job6.getConfiguration());
        cj6.setJob(job6);

        ControlledJob cj7 = new ControlledJob(job7.getConfiguration());
        cj7.setJob(job7);

        ControlledJob cj8 = new ControlledJob(job8.getConfiguration());
        cj8.setJob(job8);

        //3.对可被控制的作业添加依赖关系
        cj2.addDependingJob(cj1);

        cj3.addDependingJob(cj2);

        cj5.addDependingJob(cj3);
        cj5.addDependingJob(cj4);

        cj6.addDependingJob(cj5);

        cj7.addDependingJob(cj6);

        cj8.addDependingJob(cj7);
        //4.构建JobControl对象
        JobControl jc = new JobControl(this.getClass().getName());
        jc.addJob(cj1);
        jc.addJob(cj2);
        jc.addJob(cj3);
        jc.addJob(cj4);
        jc.addJob(cj5);
        jc.addJob(cj6);
        jc.addJob(cj7);
        jc.addJob(cj8);

        //5.构建线程对象，并启动线程
        Thread t = new Thread(jc);
        t.start();
        do{
            for(ControlledJob cj : jc.getRunningJobList()){
                cj.getJob().monitorAndPrintJob();
            }
        }while(!jc.allFinished());
        return 0;
    }

    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new GoodRecommendationManagementSystemJobController(),args));
    }
}
