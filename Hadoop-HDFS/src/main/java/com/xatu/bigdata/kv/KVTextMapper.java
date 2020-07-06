package com.xatu.bigdata.kv;


import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

//banzhang ni hao   < banzhang,1>
public class KVTextMapper extends Mapper<Text, Text, Text, IntWritable>{

    IntWritable v = new IntWritable(1);

    @Override
    protected void map(Text key, Text value, Context context)
            throws IOException, InterruptedException {




        // 2 写出
        context.write(key, v);
    }
}
