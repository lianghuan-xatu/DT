package com.xatu.bigdata.topN;

import java.io.IOException;
import java.util.Iterator;
import java.util.TreeMap;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TopNReducer extends Reducer<FlowBean, Text, Text, FlowBean> {

	// 定义一个TreeMap作为存储数据的容器（天然按key排序）
	TreeMap<FlowBean, Text> flowMap = new TreeMap<FlowBean, Text>();

	@Override
	protected void reduce(FlowBean key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {

		for (Text value : values) {

			FlowBean bean = new FlowBean();
			bean.set(key.getDownFlow(), key.getUpFlow());

			// 1 向treeMap集合中添加数据
			flowMap.put(bean, new Text(value));

			// 2 限制TreeMap数据量，超过10条就删除掉流量最小的一条数据
			if (flowMap.size() > 10) {
				// flowMap.remove(flowMap.firstKey());
				flowMap.remove(flowMap.lastKey());
			}
		}
	}

	@Override
	protected void cleanup(Context context)
			throws IOException, InterruptedException {

		// 3 遍历集合，输出数据
		Iterator<FlowBean> it = flowMap.keySet().iterator();

		while (it.hasNext()) {

			FlowBean v = it.next();

			context.write(new Text(flowMap.get(v)), v);
		}
	}
}
