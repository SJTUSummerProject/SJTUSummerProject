package com.sjtusummerproject.commentmicroservice.DataModel.Dao;

import com.sjtusummerproject.commentmicroservice.DataModel.Domain.ReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReplyRepository extends CrudRepository<ReplyEntity,Long> {
    public ReplyEntity findById(Long replyId);
    public Page<ReplyEntity> findByParentIdAndType(Long parentId, String type, Pageable pageable);
    /*用于查询*/
    public Page<ReplyEntity> findByTargetObjectId(Long targetObjectId,Pageable pageable);
    public void deleteAllByParentId(Long parentId);
    /*用于删除*/
    public List<ReplyEntity> findByTargetObjectId(Long targetId);
}
