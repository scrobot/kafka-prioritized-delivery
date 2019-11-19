package com.scrobot.kafkadelivery;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;

import java.util.concurrent.CountDownLatch;

@SpringBootTest
class KafkadeliveryApplicationTests {

	private static final Logger logger = LoggerFactory.getLogger(Producer.class);

}
