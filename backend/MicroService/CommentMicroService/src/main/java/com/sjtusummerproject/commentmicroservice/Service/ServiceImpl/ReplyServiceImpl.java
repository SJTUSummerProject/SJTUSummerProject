package com.sjtusummerproject.commentmicroservice.Service.ServiceImpl;

import com.sjtusummerproject.commentmicroservice.DataModel.Dao.ReplyRepository;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.CommentEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.ReplyEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.commentmicroservice.Service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    ReplyRepository replyRepository;

    @Override
    public ReplyEntity addToComment(UserEntity userEntity, CommentEntity commentEntity, String content) {

        ReplyEntity replyEntity = new ReplyEntity();
        replyEntity.setOwnerId(userEntity.getId());
        replyEntity.setOwnername(userEntity.getUsername());

        replyEntity.setTargetUserId(commentEntity.getOwnerId());
        replyEntity.setTargetUsername(commentEntity.getOwnername());
        replyEntity.setTargetObjectId(commentEntity.getId());
        replyEntity.setParentId(commentEntity.getId());
        replyEntity.setType("toComment");
        replyEntity.setContent(content);
        return replyRepository.save(replyEntity);
    }

    @Override
    public ReplyEntity addToReply(UserEntity ownerUser, ReplyEntity replied, Long commentId, String content) {
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
        return replyRepository.save(replyEntity);
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
