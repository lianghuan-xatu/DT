package com.xatu.bigdata.inputformat;


import java.io.IOException;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class SequenceFileReducer extends Reducer<Text, BytesWritable, Text, BytesWritable>{

    @Override
    protected void reduce(Text key, Iterable<BytesWritable> values,
                          Reducer<Text, BytesWritable, Text, BytesWritable>.Context context) throws IOException, InterruptedException {

        // 循环写出
        for (BytesWritable value : values) {
            context.write(key, value);
        }
    }
}
