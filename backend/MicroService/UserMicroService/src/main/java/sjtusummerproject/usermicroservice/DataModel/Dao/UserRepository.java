package sjtusummerproject.usermicroservice.DataModel.Dao;

import org.springframework.data.repository.CrudRepository;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findFirstByUsername(String username);

    void deleteByUsername(String username);
}
