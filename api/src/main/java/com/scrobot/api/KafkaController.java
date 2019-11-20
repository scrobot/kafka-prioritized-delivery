package com.scrobot.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class KafkaController {

    @GetMapping("kafka/{id}")
    public Mono<String> test(@PathVariable String id) {
        return Mono.just(id);
    }

}
