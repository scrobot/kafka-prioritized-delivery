package com.scrobot.kafkadelivery;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class KafkaController {

    private final Producer producer;

    public KafkaController(Producer producer) {
        this.producer = producer;
    }

    @GetMapping("kafka/{id}")
    public Mono<String> test(@PathVariable String id) {
        producer.sendMessage(id);
        return Mono.just(id);
    }

}
