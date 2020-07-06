package com.xatu.bigdata.table;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TableReducer extends Reducer<Text, TableBean, TableBean, NullWritable>{
	
	@Override
	protected void reduce(Text key, Iterable<TableBean> values,
			Context context) throws IOException, InterruptedException {
		
		// 存储所有订单集合
		ArrayList<TableBean> orderBeans = new ArrayList<TableBean>();
		// 存储产品信息
		TableBean pdBean = new TableBean();
		
		for (TableBean tableBean : values) {
			
			if ("order".equals(tableBean.getFlag())) {// 订单表
				
				TableBean tmpBean = new TableBean();
				
				try {
					BeanUtils.copyProperties(tmpBean, tableBean);
					
					orderBeans.add(tmpBean);
					
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}else {
				try {
					BeanUtils.copyProperties(pdBean, tableBean);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		for (TableBean tableBean : orderBeans) {
			tableBean.setPname(pdBean.getPname());
			
			context.write(tableBean, NullWritable.get());
		}
	}
}
