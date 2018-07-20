package com.sjtusummerproject.commentmicroservice.DataModel.Dao;

import com.sjtusummerproject.commentmicroservice.DataModel.Domain.ReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReplyRepository extends MongoRepository<ReplyEntity,Long>{
    public ReplyEntity findById(Long replyId);
    public Page<ReplyEntity> findByParentId(Long parentId, Pageable pageable);
    /*用于查询*/
    public Page<ReplyEntity> findByTargetObjectId(Long targetObjectId,Pageable pageable);
    public void deleteAllByParentId(Long parentId);
    /*用于删除*/
    public List<ReplyEntity> findByTargetObjectId(Long targetId);
}