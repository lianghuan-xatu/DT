package com.xatu.test.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

public class TimeInterceptor implements ProducerInterceptor {
    /**
     *
     * 拦截处理
     * @param producerRecord
     * @return
     */
    @Override
    public ProducerRecord onSend(ProducerRecord producerRecord) {
        //取出数据
        String record = producerRecord.value().toString();

        //创建新的ProducerRecord对象返回
        return new ProducerRecord(producerRecord.topic(),producerRecord.partition(),producerRecord.key(),System.currentTimeMillis()+","+record);
    }

    /**
     * 回执消息
     * @param recordMetadata
     * @param e
     */
    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
