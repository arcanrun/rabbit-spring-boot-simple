package com.arcanrun.rabbitmq.controller;

import com.arcanrun.rabbitmq.rabbitmq.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rabbit")
public class RabbitController {

    private final Sender sender;

    @PostMapping("/simple-message")
    public void sendMessage(@RequestBody String message) {
        sender.sendMessage(message);
    }
}
