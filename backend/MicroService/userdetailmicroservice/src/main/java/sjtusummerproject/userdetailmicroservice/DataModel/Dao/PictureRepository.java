package sjtusummerproject.userdetailmicroservice.DataModel.Dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import sjtusummerproject.userdetailmicroservice.DataModel.Domain.PictureEntity;

public interface PictureRepository extends MongoRepository<PictureEntity,String> {
    PictureEntity findByUuid(String uuid);
}
