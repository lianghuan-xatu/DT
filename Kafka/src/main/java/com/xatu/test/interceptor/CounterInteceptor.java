package com.xatu.test.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

public class CounterInteceptor implements ProducerInterceptor {
    private int succss = 0;
    private int error = 0;
    @Override
    public ProducerRecord onSend(ProducerRecord producerRecord) {
        return null;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        if(recordMetadata == null ){
            error ++;
        }else {
            succss ++;
        }

    }

    @Override
    public void close() {
        System.out.println("success：" + succss );
        System.out.println("error:" + error);
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
