package com.xatu.bigdata.index;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class OneIndexMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	String name;

	@Override
	protected void setup(Context context)
			throws IOException, InterruptedException {

		// 获取文件名称
		FileSplit inputSplit = (FileSplit) context.getInputSplit();
		name = inputSplit.getPath().getName();
	}

	Text k = new Text();
	IntWritable v = new IntWritable(1);

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		// atguigu pingping

		// 1 获取一行
		String line = value.toString();

		// 2 切割
		String[] fields = line.split(" ");

		// 3 写出
		for (String word : fields) {

			k.set(word + "--" + name);

			context.write(k, v);
		}
	}
}
