package com.xatu.test.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.Properties;

public class MyProducer {
    public static void main(String args[]){

        //创建Kafaka生产者配置信息
        Properties props = new Properties();
        //指定连接的Kafka集群
        props.put("bootstrap.servers", "192.168.0.101:9092");
        //Ack应答机制
        props.put("acks", "all");
        //重试次数
        props.put("retries", 1);
        //批次大小
        props.put("batch.size", 16384);
        //等待时间
        props.put("linger.ms", 1);
        //RecordAccumulator缓冲区大小
        props.put("buffer.memory", 33554432);

        //添加拦截器
        ArrayList<String> interceptors = new ArrayList<>();
        interceptors.add("com.xatu.test.interceptor.TimeInterceptor");
        interceptors.add("com.xatu.test.interceptor.CounterInteceptor");
        props.put("ProducerConfig.INTERCEPTOR_CLASSES_CONFIG",interceptors);



        //设置序列化
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String,String> producer = new KafkaProducer<String, String>(props);
        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<String, String>("first",
                Integer.toString(i), Integer.toString(i)));
        }
        producer.close();
        }
}
