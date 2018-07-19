package sjtusummerproject.usermicroservice.DataModel.Dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserDetailEntity;

@Repository
public interface UserDetailRepository extends MongoRepository<UserDetailEntity,Long> {
    public UserDetailEntity findById(Long id);
}
