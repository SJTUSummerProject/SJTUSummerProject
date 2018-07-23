package sjtusummerproject.userdetailmicroservice.DataModel.Dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sjtusummerproject.userdetailmicroservice.DataModel.Domain.UserDetailEntity;

@Repository
public interface UserDetailRepository extends MongoRepository<UserDetailEntity,Long> {
    public UserDetailEntity findById(Long id);
}
