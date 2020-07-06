package com.xatu.bigdata.order;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class OrderBean implements WritableComparable<OrderBean>{

	private int order_id;		// 订单id
	private double price;		// 价格
	
	public OrderBean() {
		super();
	}
	
	public OrderBean(int order_id, double price) {
		super();
		this.order_id = order_id;
		this.price = price;
	}



	public void write(DataOutput out) throws IOException {
		
		out.writeInt(order_id);
		out.writeDouble(price);
	}

	public void readFields(DataInput in) throws IOException {

		order_id = in.readInt();
		price = in.readDouble();
	}

	public int compareTo(OrderBean bean) {
		
		// 先按照定id升序排序，如果相同 按照价格降序排序
		int result;
		
		if (order_id > bean.getOrder_id()) {
			result = 1;
		}else if (order_id < bean.getOrder_id()) {
			result = -1;
		}else {
			
			if (price > bean.getPrice()) {
				result = -1;
			}else if (price < bean.getPrice()) {
				result = 1;
			}else {
				result = 0;
			}
		}
		
		return result;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return order_id + "\t" + price;
	}
}
