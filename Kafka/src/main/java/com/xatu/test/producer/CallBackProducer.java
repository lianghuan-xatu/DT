package com.xatu.test.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.Future;

public class CallBackProducer
{
    public static void main(String args[]) {
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

//
//        //设置分区
//        props.put("partitioner.class","com.xatu.test.partitioner.MyPartitioner");

        //设置序列化
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String,String> producer = new KafkaProducer<String, String>(props);
        for (int i = 0; i < 100; i++) {
            Future<RecordMetadata> first = producer.send(new ProducerRecord<String, String>("first",
                    Integer.toString(i), Integer.toString(i)), new Callback() {
                //回调函数，该方法会在Producer 收到 ack 时调用，为异步调用
                public void onCompletion(RecordMetadata metadata,
                                         Exception exception) {
                    if (exception == null) {
                        System.out.println("success->" +

                                metadata.offset());
                    } else {
                        exception.printStackTrace();
                    }
                }
            });
            try {
                RecordMetadata recordMetadata = first.get();
            }catch (Exception e){
                e.printStackTrace();
            }



        }
        producer.close();
    }
}
