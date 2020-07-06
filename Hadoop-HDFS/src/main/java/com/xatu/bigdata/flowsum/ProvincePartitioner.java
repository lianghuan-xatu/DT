package com.xatu.bigdata.flowsum;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class ProvincePartitioner extends Partitioner<Text,FlowBean>
{
    public int getPartition(Text text, FlowBean flowBean, int i) {

        //key  手机号
        //value   流量信息

        //获取手机号前三位
        String prePhoneNum = text.toString().substring(0, 3);

        int partition = 4;
        if("136".equals(prePhoneNum)) {
            partition = 0;
        }else  if("137".equals(prePhoneNum)) {
            partition = 1;
        }else if("138".equals(prePhoneNum)) {
            partition = 2;
        }else if("139".equals(prePhoneNum)) {
            partition = 3;
        }
        return partition;
    }
}
