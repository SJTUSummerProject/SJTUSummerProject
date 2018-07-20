package com.sjtusummerproject.commentmicroservice.Component;


import com.sjtusummerproject.commentmicroservice.Config.RabbitCommentMQConfig;
import com.sjtusummerproject.commentmicroservice.Config.RabbitReplyCommentMQConfig;
import com.sjtusummerproject.commentmicroservice.DataModel.Dao.CommentRepository;
import com.sjtusummerproject.commentmicroservice.DataModel.Dao.ReplyRepository;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.CommentEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.ReplyEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.commentmicroservice.Service.CommentService;
import com.sjtusummerproject.commentmicroservice.Service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Component
public class ReceiveReplyCommentMessageComponent {
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ReplyRepository replyRepository;

    @RabbitListener(queues = RabbitReplyCommentMQConfig.QUEUE_NAME)
    public void consumeMessage(MultiValueMap<String, String> message) {
        System.out.println("in receive reply comment ");
        Long userid = Long.parseLong(message.getFirst("ownerId"));
        Long targetTicketId = Long.parseLong(message.getFirst("targetTicketId"));
        String content = message.getFirst("content");

        UserEntity userEntity = userService.queryById(userid);
        CommentEntity commentEntity = commentService.queryByCommentId(targetTicketId);

        ReplyEntity replyEntity = new ReplyEntity();
        replyEntity.setOwnerId(userEntity.getId());
        replyEntity.setOwnername(userEntity.getUsername());

        replyEntity.setTargetUserId(commentEntity.getOwnerId());
        replyEntity.setTargetUsername(commentEntity.getOwnername());
        replyEntity.setTargetObjectId(commentEntity.getId());
        replyEntity.setParentId(commentEntity.getId());
        replyEntity.setType("toComment");
        replyEntity.setContent(content);
        replyRepository.save(replyEntity);
    }
}



