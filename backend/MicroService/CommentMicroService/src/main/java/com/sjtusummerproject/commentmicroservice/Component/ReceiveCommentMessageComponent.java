package com.sjtusummerproject.commentmicroservice.Component;

import com.sjtusummerproject.commentmicroservice.Config.RabbitCommentMQConfig;
import com.sjtusummerproject.commentmicroservice.DataModel.Dao.CommentRepository;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.CommentEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.commentmicroservice.Service.AuthService;
import com.sjtusummerproject.commentmicroservice.Service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Date;


@Component
public class ReceiveCommentMessageComponent {
    @Autowired
    UserService userService;
    @Autowired
    CommentRepository commentRepository;

    @Value("${commentservice.url}")
    String commentServiceUrl;
    @Autowired
    AuthService authService;

    @RabbitListener(queues = RabbitCommentMQConfig.CommentQUEUE_NAME)
    public void consumeMessage(MultiValueMap<String, Object> message) {
        String token = (String) message.getFirst("token");
        Long targetTicketId = (Long) message.getFirst("targetTicketId");
        String content = (String) message.getFirst("content");
        try {
            UserEntity user = authService.callAuthService(token);
            Date createTime = new Date();
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setId(0L);
            commentEntity.setOwnerId(user.getId());
            commentEntity.setOwnername(user.getUsername());
            commentEntity.setTargetTicketId(targetTicketId);
            commentEntity.setContent(content);
            commentEntity.setCreateTime(new Date());
            commentRepository.save(commentEntity);
            commentEntity = commentRepository.findByOwnerIdAndContentAndCreateTimeAndTargetTicketId(user.getId(), content, createTime, targetTicketId);
            String replys = commentServiceUrl + "/Reply/QueryByParentId" + "?parentid=" + commentEntity.getId() + "&pagenumber=1" + "&type=toComment";
            commentEntity.setReplys(replys);
            commentRepository.save(commentEntity);
        }catch (Exception e){
            System.out.println("there is a error in rabbitmq in comment");
        }
    }
}


