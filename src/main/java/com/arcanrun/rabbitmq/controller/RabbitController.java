package com.arcanrun.rabbitmq.controller;

import com.arcanrun.rabbitmq.rabbitmq.RabbitMQSender;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/rabbit")
@Api(tags = "Rabbit controller")
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

    @PostMapping("/direct/{routingKey}")
    public void sendMessageWithRoutingKey(@RequestBody String message, @PathVariable String routingKey) {
        sender.sendMessageWithRoutingKey(message, routingKey);
    }

    @PostMapping("/topic")
    public void sendMessageToTopics(@RequestBody String message, @RequestParam String key) {
        sender.sendMessageToTopics(message, key);
    }
}
