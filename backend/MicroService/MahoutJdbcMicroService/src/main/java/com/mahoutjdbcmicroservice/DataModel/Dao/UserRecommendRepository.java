package com.mahoutjdbcmicroservice.DataModel.Dao;

import com.mahoutjdbcmicroservice.DataModel.Domain.UserRecommendEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRecommendRepository extends CrudRepository<UserRecommendEntity,Long> {
    public UserRecommendEntity findById(Long id);
}
