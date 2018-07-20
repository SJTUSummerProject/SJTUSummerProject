package com.sjtusummerproject.commentmicroservice.Component;

import com.sjtusummerproject.commentmicroservice.Config.RabbitCommentMQConfig;
import com.sjtusummerproject.commentmicroservice.DataModel.Dao.CommentRepository;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.CommentEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.commentmicroservice.Service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Component
public class ReceiveCommentMessageComponent {
    @Autowired
    UserService userService;
    @Autowired
    CommentRepository commentRepository;

    @Value("${commentservice.url}")
    String commentServiceUrl;

    @RabbitListener(queues = RabbitCommentMQConfig.QUEUE_NAME)
    public void consumeMessage(MultiValueMap<String, String> message) {
        System.out.println("in receive comment ");
        Long userid = Long.parseLong(message.getFirst("ownerId"));
        Long targetTicketId = Long.parseLong(message.getFirst("targetTicketId"));
        String content = message.getFirst("content");

        UserEntity user = userService.queryById(userid);

        Date createTime = new Date();
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setOwnerId(user.getId());
        commentEntity.setOwnername(user.getUsername());
        commentEntity.setTargetTicketId(targetTicketId);
        commentEntity.setContent(content);
        commentEntity.setCreateTime(new Date());
        commentRepository.save(commentEntity);
        commentEntity = commentRepository.findByOwnerIdAndContentAndCreateTimeAndTargetTicketId(user.getId(),content,createTime,targetTicketId);
        String replys = commentServiceUrl+"/Reply/QueryByParentId"+"?parentid="+commentEntity.getId()+"&pagenumber=1"+"&type=toComment";
        commentEntity.setReplys(replys);
        commentRepository.save(commentEntity);
    }
}


