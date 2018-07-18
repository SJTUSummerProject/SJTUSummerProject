package sjtusummerproject.usermicroservice.DataModel.Dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserDetailEntity;

public interface UserDetailRepository extends MongoRepository<UserDetailEntity,Long> {
    public UserDetailEntity findById(Long id);
}
