package com.sjtusummerproject.picturemicroservice.DataModel.Dao;

import com.sjtusummerproject.picturemicroservice.DataModel.Domain.PictureEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PictureRepository extends MongoRepository<PictureEntity,String> {
    public PictureEntity findByUuid(String uuid);
}
