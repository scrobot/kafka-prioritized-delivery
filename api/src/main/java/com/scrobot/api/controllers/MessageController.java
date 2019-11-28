package com.scrobot.api.controllers;

import com.scrobot.api.business.MessagingService;
import com.scrobot.api.models.RequestMessage;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessagingService service;

    public MessageController(MessagingService service) {
        this.service = service;
    }

    @GetMapping("all")
    public Mono<List<String>> all() {
        return service.getAllMessages()
            .map(Collections::singletonList);
    }

    @PostMapping("create")
    public Mono<String> create(@RequestBody RequestMessage message) {
        return service.saveMessage(message);
    }

}
