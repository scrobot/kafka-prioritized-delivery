package com.scrobot.api.data;

import com.scrobot.api.models.RequestMessage;
import org.springframework.stereotype.Component;

@Component
public class MessagesRepository {

    private final MessagesProducer producer;
    private final KsqlRequest ksqlRequest = new KsqlRequest();

    public MessagesRepository(MessagesProducer producer) {
        this.producer = producer;
    }

    public void storeMessage(RequestMessage requestMessage) {
        producer.storeMessage(requestMessage);
    }

    public String getAllMessages() {
        return ksqlRequest.getAllMessages();
    }
}
