package sjtusummerproject.usermicroservice.DataModel.Dao;

import org.springframework.data.repository.CrudRepository;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserDetailEntity;

public interface UserDetailRepository extends CrudRepository<UserDetailEntity,Long> {
    public UserDetailEntity findById(Long id);
}
