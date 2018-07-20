package com.sjtusummerproject.commentmicroservice.Service.ServiceImpl;

//import com.sjtusummerproject.commentmicroservice.Config.RabbitReplyCommentMQConfig;
//import com.sjtusummerproject.commentmicroservice.Config.RabbitReplyReplyMQConfig;
import com.sjtusummerproject.commentmicroservice.DataModel.Dao.ReplyRepository;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.CommentEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.ReplyEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.commentmicroservice.Service.ReplyService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Date;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    ReplyRepository replyRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public String addToComment(Long ownerId, Long commentId, String content) {
        System.out.println("in invoke reply comment");
        MultiValueMap<String,Object> message = new LinkedMultiValueMap<>();
        message.add("ownerId",ownerId);
        message.add("targetTicketId",commentId);
        message.add("content",content);
//        rabbitTemplate.convertAndSend(RabbitReplyCommentMQConfig.EXCHANGE_NAME, RabbitReplyCommentMQConfig.ROUTING_KEY, message);
        return "ok";
    }

    @Override
    public String addToReply(Long ownerId, Long repliedId, Long commentId, String content) {
        System.out.println("in invoke reply reply");
        MultiValueMap<String,Object> message = new LinkedMultiValueMap<>();
        message.add("ownerId",ownerId);
        message.add("repliedId",repliedId);
        message.add("commentId",commentId);
        message.add("content",content);
//        rabbitTemplate.convertAndSend(RabbitReplyReplyMQConfig.EXCHANGE_NAME, RabbitReplyReplyMQConfig.ROUTING_KEY, message);
        return "ok";
    }

    @Override
    public Page<ReplyEntity> queryByParentId(Long parentId, Pageable pageable) {
        return replyRepository.findByParentId(parentId,pageable);
    }

    @Override
    public Page<ReplyEntity> queryByTargetObjectId(Long targetId, Pageable pageable) {
        return replyRepository.findByTargetObjectId(targetId,pageable);
    }

    @Override
    public String deleteById(Long replyId) {
        /*replyid 是唯一的 不会成环删除*/
        List<ReplyEntity> replysToReply = replyRepository.findByTargetObjectId(replyId);
        for(ReplyEntity eachReply : replysToReply){
            deleteById(eachReply.getId());
        }
        replyRepository.delete(replyId);
        return "ok";
    }

    @Override
    public String deleteByParentId(Long parentId) {
        replyRepository.deleteAllByParentId(parentId);
        return "ok";
    }

    @Override
    public ReplyEntity queryById(Long replyId) {
        return replyRepository.findById(replyId);
    }
}