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

public interface CommentService {
    public String save(String token, Long targetTicketId, String content);
    public Page<CommentEntity> queryByOwnerId(Long ownerid, Pageable pageable);
    public Page<CommentEntity> queryByTicketId(Long ticketid, Pageable pageable);
    public CommentEntity queryByCommentId(Long commentId);
    public CommentEntity updateContentByCommentid(Long commentId, String content);
    public CommentEntity deleteByCommentid(Long commentId);
}
