package com.sjtusummerproject.commentmicroservice.DataModel.Dao;

import com.sjtusummerproject.commentmicroservice.DataModel.Domain.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface CommentRepository extends CrudRepository<CommentEntity,Long> {
    public Page<CommentEntity> findByOwnerId(Long ownerid, Pageable pageable);
    public Page<CommentEntity> findByTargetTicketId(Long ticketid, Pageable pageable);
    public CommentEntity findById(Long commentId);
    public CommentEntity deleteById(Long commentId);
    public CommentEntity findByOwnerIdAndContentAndCreateTimeAndTargetTicketId(Long ownerId,String content,Date createTime,Long targetTicketId);
}
