package com.xatu.bigdata.flowsum;

import com.xatu.bigdata.wordcount.WordCountDriver;
import com.xatu.bigdata.wordcount.WordCountMapper;
import com.xatu.bigdata.wordcount.WordCountReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


import java.io.IOException;

public class FlowSumDriver
{
    public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {

        args = new String[]{"d:/input/inputflow","d:/output"};
        Configuration conf = new Configuration();
        //获取Job对象
        Job job = Job.getInstance(conf);
        //获取jar存储位置
        job.setJarByClass(FlowSumDriver.class);
        //关联Map和Reduce类
        job.setMapperClass(FlowCountMapper.class);
        job.setReducerClass(FlowCountReducer.class);
        //设置Mapper阶段输出数据的key和value类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
        //设置最终数据输出的key和value类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);
        //设置输入和输出路径
        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));
        //提交job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
        }
}
