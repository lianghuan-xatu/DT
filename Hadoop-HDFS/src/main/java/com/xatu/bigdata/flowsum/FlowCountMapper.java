package com.xatu.bigdata.flowsum;

import com.sun.tools.javac.comp.Flow;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowCountMapper extends Mapper<LongWritable, Text,Text,FlowBean> {

    Text k = new Text();
    FlowBean v = new FlowBean();


    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //获取一行
        String line = value.toString();
        //切割 \t
        String[] fields = line.split("\t");
        //封装对象
        k.set(fields[1]);  //封装手机号
        long upFlow = Long.parseLong(fields[fields.length-3]);
        long downFlow = Long.parseLong(fields[fields.length-2]);

        v.setUpFlow(upFlow);
        v.setDownFlow(downFlow);

        //写出
        context.write(k,v);
    }
}
