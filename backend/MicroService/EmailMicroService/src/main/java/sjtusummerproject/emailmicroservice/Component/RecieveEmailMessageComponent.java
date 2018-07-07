package sjtusummerproject.emailmicroservice.Component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import sjtusummerproject.emailmicroservice.Config.RabbitMQConfig;
import sjtusummerproject.emailmicroservice.DataModel.Domain.UserUuidEntity;
import sjtusummerproject.emailmicroservice.Service.SendEmailService;
import sjtusummerproject.emailmicroservice.Service.UserUuidManageService;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Component
public class RecieveEmailMessageComponent {
    @Autowired
    SendEmailService sendEmailService;

    @Autowired
    UserUuidManageService userUuidManageService;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumeMessage(MultiValueMap<String,String> message){
        System.out.println("the user ");
        String email = message.getFirst("email");
        System.out.println("the email "+email);
        UUID code = UUID.randomUUID();
        UserUuidEntity userUuidEntity = new UserUuidEntity();
        userUuidEntity.setUsername(message.getFirst("username"));
        userUuidEntity.setUuid(code.toString());

        redisTemplate.opsForValue().set(code.toString(),message.getFirst("username"),24,TimeUnit.HOURS);

        sendEmailService.sendMail(email,code.toString());
    }
}


