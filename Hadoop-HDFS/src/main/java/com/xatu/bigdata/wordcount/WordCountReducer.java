package com.xatu.bigdata.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WordCountReducer extends Reducer<Text, IntWritable,Text,IntWritable>
{
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //累加求和
        int sum = 0;
        for (IntWritable value : values) {
            sum += value.get();
        }
        IntWritable v = new IntWritable();
        v.set(sum);
        //写出
        context.write(key,v);
        }
}
