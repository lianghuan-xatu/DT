package com.xatu.bigdata.order;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class OrderGroupingComparator extends WritableComparator{

	protected OrderGroupingComparator(){
		super(OrderBean.class, true);
	}
	
	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		// 要求只要id相同，就认为是相同的key
		
		OrderBean aBean = (OrderBean) a;
		OrderBean bBean = (OrderBean) b;
		
		int result;
		if (aBean.getOrder_id() > bBean.getOrder_id()) {
			result = 1;
		}else if(aBean.getOrder_id() < bBean.getOrder_id()){
			result = -1;
		}else {
			result = 0;
		}
		
		return result;
	}
	
}
