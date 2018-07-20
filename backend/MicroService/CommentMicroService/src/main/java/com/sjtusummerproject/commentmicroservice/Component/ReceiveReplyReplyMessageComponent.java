package com.sjtusummerproject.commentmicroservice.Component;

import com.sjtusummerproject.commentmicroservice.Config.RabbitReplyReplyMQConfig;
import com.sjtusummerproject.commentmicroservice.DataModel.Dao.CommentRepository;
import com.sjtusummerproject.commentmicroservice.DataModel.Dao.ReplyRepository;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.CommentEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.ReplyEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.commentmicroservice.Service.CommentService;
import com.sjtusummerproject.commentmicroservice.Service.ReplyService;
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
public class ReceiveReplyReplyMessageComponent {
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ReplyRepository replyRepository;
    @Autowired
    ReplyService replyService;

    @RabbitListener(queues = RabbitReplyReplyMQConfig.QUEUE_NAME)
    public void consumeMessage(MultiValueMap<String, String> message) {
        System.out.println("in receive reply reply ");
        Long userid = Long.parseLong(message.getFirst("ownerId"));
        Long repliedId = Long.parseLong(message.getFirst("repliedId"));
        Long commentId = Long.parseLong(message.getFirst("commentId"));
        String content = message.getFirst("content");

        UserEntity ownerUser = userService.queryById(userid);
        ReplyEntity replied = replyService.queryById(repliedId);

        ReplyEntity replyEntity = new ReplyEntity();

        replyEntity.setOwnerId(ownerUser.getId());
        replyEntity.setOwnername(ownerUser.getUsername());

        replyEntity.setTargetUserId(replied.getOwnerId());
        replyEntity.setTargetUsername(replied.getOwnername());
        replyEntity.setTargetObjectId(replied.getId());
        replyEntity.setParentId(commentId);
        replyEntity.setType("toReply");
        replyEntity.setContent(content);
        replyEntity.setCreateTime(new Date());
        replyRepository.save(replyEntity);
    }
}



