package com.arcanrun.rabbitmq.controller;

import com.arcanrun.rabbitmq.rabbitmq.RabbitMQSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rabbit")
public class RabbitController {

    private final RabbitMQSender sender;


    @PostMapping("/default-exchange")
    public void sendMessageDefaultExchange(@RequestBody String message) {
        sender.sendMessageToDefaultExchange(message);
    }

    @PostMapping("/fanout")
    public void sendMessageFanout(@RequestBody String message) {
        sender.sendMessageFanout(message);
    }
}
