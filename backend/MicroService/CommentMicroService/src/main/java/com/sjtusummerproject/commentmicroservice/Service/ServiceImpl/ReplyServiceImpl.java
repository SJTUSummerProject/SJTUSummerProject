package com.sjtusummerproject.commentmicroservice.Service.ServiceImpl;

import com.sjtusummerproject.commentmicroservice.Config.RabbitCommentMQConfig;
import com.sjtusummerproject.commentmicroservice.DataModel.Dao.ReplyRepository;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.ReplyEntity;
import com.sjtusummerproject.commentmicroservice.Service.ReplyService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    ReplyRepository replyRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public String addToComment(String token, Long commentId, String content) {
        System.out.println("in invoke reply comment");
        MultiValueMap<String,Object> message = new LinkedMultiValueMap<String, Object>();
        message.add("token",token);
        message.add("targetTicketId",commentId);
        message.add("content",content);
        rabbitTemplate.convertAndSend(RabbitCommentMQConfig.CommentEXCHANGE_NAME, RabbitCommentMQConfig.ReplyCommentROUTING_KEY, message);
        return "ok";
    }

    @Override
    public String addToReply(String token, Long repliedId, String content) {
        System.out.println("in invoke reply reply");
        MultiValueMap<String,Object> message = new LinkedMultiValueMap<String, Object>();
        message.add("token",token);
        message.add("repliedId",repliedId);
        message.add("content",content);
        rabbitTemplate.convertAndSend(RabbitCommentMQConfig.CommentEXCHANGE_NAME, RabbitCommentMQConfig.ReplyReplyROUTING_KEY, message);
        return "ok";
    }

    @Override
    public Page<ReplyEntity> queryByParentId(Long parentId, String type, Pageable pageable) {
        return replyRepository.findByParentIdAndType(parentId,type,pageable);
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
