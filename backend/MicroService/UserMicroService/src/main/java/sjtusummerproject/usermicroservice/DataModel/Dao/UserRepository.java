package sjtusummerproject.usermicroservice.DataModel.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findFirstByUsername(String username);
    UserEntity findByUsername(String username);
    UserEntity findById(Long id);
    Page<UserEntity> findAll(Pageable pageable);
}
