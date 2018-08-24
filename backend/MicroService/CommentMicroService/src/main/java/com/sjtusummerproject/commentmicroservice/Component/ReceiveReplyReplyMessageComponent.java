package com.sjtusummerproject.commentmicroservice.Component;

import com.sjtusummerproject.commentmicroservice.Config.RabbitCommentMQConfig;
import com.sjtusummerproject.commentmicroservice.DataModel.Dao.CommentRepository;
import com.sjtusummerproject.commentmicroservice.DataModel.Dao.ReplyRepository;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.ReplyEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.commentmicroservice.Service.AuthService;
import com.sjtusummerproject.commentmicroservice.Service.CommentService;
import com.sjtusummerproject.commentmicroservice.Service.ReplyService;
import com.sjtusummerproject.commentmicroservice.Service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Date;


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
    @Autowired
    AuthService authService;

    @RabbitListener(queues = RabbitCommentMQConfig.ReplyReplyQUEUE_NAME)
    public void consumeMessage(MultiValueMap<String, Object> message) {
        System.out.println("in receive reply reply ");
        String token = (String)message.getFirst("token");
        Long repliedId = (Long)message.getFirst("repliedId");
        String content = (String)message.getFirst("content");

        UserEntity ownerUser = authService.callAuthService(token);
        ReplyEntity replied = replyService.queryById(repliedId);

        ReplyEntity replyEntity = new ReplyEntity();

        replyEntity.setId(0L);
        replyEntity.setOwnerId(ownerUser.getId());
        replyEntity.setOwnername(ownerUser.getUsername());

        replyEntity.setTargetUserId(replied.getOwnerId());
        replyEntity.setTargetUsername(replied.getOwnername());
        replyEntity.setTargetObjectId(replied.getId());
        replyEntity.setParentId(repliedId);
        replyEntity.setType("toReply");
        replyEntity.setContent(content);
        replyEntity.setCreateTime(new Date());
        replyRepository.save(replyEntity);
    }
}



