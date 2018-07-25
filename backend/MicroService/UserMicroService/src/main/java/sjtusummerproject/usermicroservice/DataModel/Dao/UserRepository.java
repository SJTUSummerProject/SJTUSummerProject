package sjtusummerproject.usermicroservice.DataModel.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findFirstByUsername(String username);
    UserEntity findByUsername(String username);
    UserEntity findById(Long id);
}
