package sjtusummerproject.emailmicroservice.DataModel.Dao;

import org.springframework.data.repository.CrudRepository;
import sjtusummerproject.emailmicroservice.DataModel.Domain.UserUuidEntity;

public interface UserUuidRepository extends CrudRepository<UserUuidEntity,String> {
    public UserUuidEntity findByUuid(String uuid);
}
