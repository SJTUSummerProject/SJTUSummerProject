package com.sjtusummerproject.commentmicroservice.Service.ServiceImpl;

import com.sjtusummerproject.commentmicroservice.Config.RabbitCommentMQConfig;
import com.sjtusummerproject.commentmicroservice.DataModel.Dao.CommentRepository;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.CommentEntity;
import com.sjtusummerproject.commentmicroservice.Service.CommentService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${commentservice.url}")
    String commentServiceUrl;

    @Override
    public String save(String token, Long targetTicketId, String content) {
        System.out.println("in invoke comment");
        MultiValueMap<String,Object> message = new LinkedMultiValueMap<String, Object>();
        message.add("token",token);
        message.add("targetTicketId",targetTicketId);
        message.add("content",content);
        rabbitTemplate.convertAndSend(RabbitCommentMQConfig.CommentEXCHANGE_NAME, RabbitCommentMQConfig.CommentROUTING_KEY, message);
        return "ok";
    }

    @Override
    public Page<CommentEntity> queryByOwnerId(Long ownerid, Pageable pageable) {
        return commentRepository.findByOwnerId(ownerid,pageable);
    }

    @Override
    public Page<CommentEntity> queryByTicketId(Long ticketid, Pageable pageable) {
        return commentRepository.findByTargetTicketId(ticketid,pageable);
    }

    @Override
    public CommentEntity queryByCommentId(Long commentId) {
        return commentRepository.findById(commentId);
    }

    @Override
    public CommentEntity updateContentByCommentid(Long commentId, String content) {
        CommentEntity commentEntity = commentRepository.findById(commentId);
        commentEntity.setContent(content);
        /* 重新设置时间 */
        commentEntity.setCreateTime(new Date());
        return commentRepository.save(commentEntity);
    }

    @Override
    public CommentEntity deleteByCommentid(Long commentId) {
        return commentRepository.deleteById(commentId);
    }
}
