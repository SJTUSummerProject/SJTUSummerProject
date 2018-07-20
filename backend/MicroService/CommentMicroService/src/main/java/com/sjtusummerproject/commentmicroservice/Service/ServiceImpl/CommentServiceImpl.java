package com.sjtusummerproject.commentmicroservice.Service.ServiceImpl;

import com.sjtusummerproject.commentmicroservice.DataModel.Dao.CommentRepository;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.CommentEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.ReplyEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.commentmicroservice.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Value("${commentservice.url}")
    String commentServiceUrl;
    @Override
    public CommentEntity save(UserEntity user, Long targetTicketId, String content) {
        Date createTime = new Date();
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setOwnerId(user.getId());
        commentEntity.setOwnername(user.getUsername());
        commentEntity.setTargetTicketId(targetTicketId);
        commentEntity.setContent(content);
        commentEntity.setCreateTime(new Date());
        commentRepository.save(commentEntity);
        commentEntity = commentRepository.findByOwnerIdAndContentAndCreateTimeAndTargetTicketId(user.getId(),content,createTime,targetTicketId);
        String replys = commentServiceUrl+"/Reply/QueryByParentId"+"?parentid="+commentEntity.getId()+"&pagenumber=1";
        commentEntity.setReplys(replys);
        return commentRepository.save(commentEntity);
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
