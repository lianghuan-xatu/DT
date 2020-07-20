package com.xatu.video;

import java.io.IOException;

import com.xatu.util.ETLUtil;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class ETLMapper
        extends Mapper<LongWritable, Text, NullWritable, Text>
{
    private Text v = new Text();

    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, NullWritable, Text>.Context context)
            throws IOException, InterruptedException
    {
        String oriStr = value.toString();

        String etlStr = ETLUtil.etlStr(oriStr);
        if (etlStr == null) {
            return;
        }
        this.v.set(etlStr);
        context.write(NullWritable.get(), this.v);
    }
}
