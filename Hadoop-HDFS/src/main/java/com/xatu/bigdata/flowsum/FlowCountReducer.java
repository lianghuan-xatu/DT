package com.xatu.bigdata.flowsum;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowCountReducer extends Reducer<Text,FlowBean,Text,FlowBean> {

    FlowBean v = new FlowBean();
    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        //累计求和
        long sum_upFlow = 0;
        long sum_downFlow = 0;

        for (FlowBean value : values) {

            sum_upFlow += value.getUpFlow();
            sum_downFlow += value.getDownFlow();

        }
        v.setUpFlow(sum_upFlow);
        v.setDownFlow(sum_downFlow);

        //写出
        context.write(key,v);
    }
}
