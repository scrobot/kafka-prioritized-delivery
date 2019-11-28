package com.scrobot.api.data;

import com.scrobot.api.models.RequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessagesProducer {

    private static final Logger logger = LoggerFactory.getLogger(MessagesProducer.class);
    private static final String TOPIC = "messages_2";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public MessagesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void storeMessage(RequestMessage message) {
        logger.info(String.format("#### -> Producing message -> %s", message));
        this.kafkaTemplate.send(TOPIC, message.toJson());
    }

}
