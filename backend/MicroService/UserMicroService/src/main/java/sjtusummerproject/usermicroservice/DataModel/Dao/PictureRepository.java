package sjtusummerproject.usermicroservice.DataModel.Dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import sjtusummerproject.usermicroservice.DataModel.Domain.PictureEntity;

public interface PictureRepository extends MongoRepository<PictureEntity,String> {
    public PictureEntity findByUuid(String uuid);
}
