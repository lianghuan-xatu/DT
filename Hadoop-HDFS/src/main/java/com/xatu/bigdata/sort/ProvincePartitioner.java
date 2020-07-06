package com.xatu.bigdata.sort;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class ProvincePartitioner extends Partitioner<FlowBean, Text>{

	@Override
	public int getPartition(FlowBean key, Text value, int numPartitions) {
		
		// 按照手机号的前三位分区
		String prePhoneNum = value.toString().substring(0, 3);
		
		int partiton = 4;
		
		if ("136".equals(prePhoneNum)) {
			partiton = 0;
		}else if ("137".equals(prePhoneNum)) {
			partiton = 1;
		}else if ("138".equals(prePhoneNum)) {
			partiton = 2;
		}else if ("139".equals(prePhoneNum)) {
			partiton = 3;
		}
		
		return partiton;
	}
}
