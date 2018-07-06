package sjtusummerproject.signmicroservice.Service.ServiceImpl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sjtusummerproject.signmicroservice.Config.RabbitMQConfig;
import cn.hutool.core.date.DateTime;
import sjtusummerproject.signmicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.signmicroservice.Service.InvokeEmailMessageService;

import java.util.HashMap;

@Service
public class InvokeEmailMessageServiceImpl implements InvokeEmailMessageService{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Object AddEmailServiceRabbit(UserEntity user) {
        System.out.println("in add email service");
        String value = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        System.out.println("send message {}"+value);
        HashMap<String,Object> message = new HashMap<>();
        message.put("user",user);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message);
        return "ok";
    }
}
