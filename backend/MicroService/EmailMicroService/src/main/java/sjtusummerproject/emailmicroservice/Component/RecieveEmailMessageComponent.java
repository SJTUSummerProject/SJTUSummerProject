package sjtusummerproject.emailmicroservice.Component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import sjtusummerproject.emailmicroservice.Config.RabbitMQConfig;

import sjtusummerproject.emailmicroservice.Service.SendEmailService;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Component
public class RecieveEmailMessageComponent {
    @Autowired
    SendEmailService sendEmailService;

	@Autowired
    @Qualifier("redisTemplate")
    RedisTemplate redisTemplate;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumeMessage(MultiValueMap<String,String> message){
        System.out.println("the user ");
        String email = message.getFirst("email");
        System.out.println("the email "+email);
        UUID code = UUID.randomUUID();

        redisTemplate.opsForValue().set(code.toString(),message.getFirst("username"),24,TimeUnit.HOURS);

        sendEmailService.sendMail(email,code.toString());
    }
}


