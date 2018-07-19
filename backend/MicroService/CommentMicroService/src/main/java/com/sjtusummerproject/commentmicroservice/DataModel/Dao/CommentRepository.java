package com.sjtusummerproject.commentmicroservice.DataModel.Dao;

import com.sjtusummerproject.commentmicroservice.DataModel.Domain.CommentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<CommentEntity,Long> {

}
