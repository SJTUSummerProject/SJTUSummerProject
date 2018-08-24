package com.sjtusummerproject.commentmicroservice.Component;

import com.sjtusummerproject.commentmicroservice.Config.RabbitCommentMQConfig;
import com.sjtusummerproject.commentmicroservice.DataModel.Dao.CommentRepository;
import com.sjtusummerproject.commentmicroservice.DataModel.Dao.ReplyRepository;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.CommentEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.ReplyEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.commentmicroservice.Service.AuthService;
import com.sjtusummerproject.commentmicroservice.Service.CommentService;
import com.sjtusummerproject.commentmicroservice.Service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;


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
    @Autowired
    AuthService authService;

    @RabbitListener(queues = RabbitCommentMQConfig.ReplyCommentQUEUE_NAME)
    public void consumeMessage(MultiValueMap<String, Object> message) {
        String token = (String)message.getFirst("token");
        Long targetTicketId = (Long)message.getFirst("targetTicketId");
        String content = (String)message.getFirst("content");

        UserEntity userEntity = authService.callAuthService(token);
        CommentEntity commentEntity = commentService.queryByCommentId(targetTicketId);

        ReplyEntity replyEntity = new ReplyEntity();
        replyEntity.setId(0L);
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



