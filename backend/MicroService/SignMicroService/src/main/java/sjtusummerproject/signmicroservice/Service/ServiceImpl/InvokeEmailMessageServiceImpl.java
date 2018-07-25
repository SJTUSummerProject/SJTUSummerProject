package sjtusummerproject.signmicroservice.Service.ServiceImpl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import sjtusummerproject.signmicroservice.Config.RabbitMQConfig;
import cn.hutool.core.date.DateTime;
import sjtusummerproject.signmicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.signmicroservice.Service.InvokeEmailMessageService;

import java.util.HashMap;

@Service
public class InvokeEmailMessageServiceImpl implements InvokeEmailMessageService{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public String AddEmailServiceRabbit(UserEntity user) {
        System.out.println("in add email service");
        MultiValueMap<String,String> message = new LinkedMultiValueMap<>();
        message.add("username",user.getUsername());
        message.add("password",user.getPassword());
        message.add("email",user.getEmail());
        message.add("status",user.getStatus());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message);
        return "ok";
    }
}
