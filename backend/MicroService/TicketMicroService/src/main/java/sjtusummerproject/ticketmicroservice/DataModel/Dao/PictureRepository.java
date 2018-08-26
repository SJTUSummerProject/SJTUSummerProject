package sjtusummerproject.ticketmicroservice.DataModel.Dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import sjtusummerproject.ticketmicroservice.DataModel.Domain.PictureEntity;

public interface PictureRepository extends MongoRepository<PictureEntity,String> {
    PictureEntity findByUuid(String uuid);
}

