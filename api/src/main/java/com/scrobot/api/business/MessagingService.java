package com.scrobot.api.business;

import com.scrobot.api.data.MessagesRepository;
import com.scrobot.api.models.RequestMessage;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessagingService {

    private final MessagesRepository messagesRepository;

    public MessagingService(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    public Mono<String> getAllMessages() {
        return Mono.just(messagesRepository.getAllMessages());
    }

    public Mono<String> saveMessage(RequestMessage message) {
        return Mono.just(message)
            .doOnSuccess(messagesRepository::storeMessage)
            .map(RequestMessage::getId);
    }

}
