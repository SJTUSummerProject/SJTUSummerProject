package sjtusummerproject.emailmicroservice.Component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import sjtusummerproject.emailmicroservice.Config.RabbitMQConfig;
import sjtusummerproject.emailmicroservice.DataModel.Domain.UserUuidEntity;
import sjtusummerproject.emailmicroservice.Service.SendEmailService;
import sjtusummerproject.emailmicroservice.Service.UserUuidManageService;

import java.util.HashMap;
import java.util.UUID;

@Component
public class RecieveEmailMessageComponent {
    @Autowired
    SendEmailService sendEmailService;

    @Autowired
    UserUuidManageService userUuidManageService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumeMessage(MultiValueMap<String,String> message){
        System.out.println("the user ");
        String email = message.getFirst("email");
        System.out.println("the email "+email);
        UUID code = UUID.randomUUID();
        UserUuidEntity userUuidEntity = new UserUuidEntity();
        userUuidEntity.setUsername(message.getFirst("username"));
        userUuidEntity.setUuid(code.toString());

        userUuidManageService.AddUserUuidService(userUuidEntity);
        sendEmailService.sendMail(email,code.toString());
    }
}


