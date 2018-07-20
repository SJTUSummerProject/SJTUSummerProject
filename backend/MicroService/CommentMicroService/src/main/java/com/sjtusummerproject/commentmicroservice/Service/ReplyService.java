package com.sjtusummerproject.commentmicroservice.Service;

import com.sjtusummerproject.commentmicroservice.DataModel.Domain.CommentEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.ReplyEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

public interface ReplyService {
    public String addToComment(String token, Long commentId, String content);
    public String addToReply(String token, Long repliedId, String content);
    public String deleteById(Long replyId);
    public String deleteByParentId(Long parentId);
    public Page<ReplyEntity> queryByParentId(Long parentId, String type, Pageable pageable);
    public Page<ReplyEntity> queryByTargetObjectId(Long targetId, Pageable pageable);
    public ReplyEntity queryById(Long replyId);
}
