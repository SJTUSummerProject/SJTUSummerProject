package com.testmq.rabbitmq;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Console;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Send {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMessage")
    public Object sendMessage() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                String value = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
                System.out.println("send message {}"+value);
                rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, value);
            }
        }).start();
        return "ok";
    }

}