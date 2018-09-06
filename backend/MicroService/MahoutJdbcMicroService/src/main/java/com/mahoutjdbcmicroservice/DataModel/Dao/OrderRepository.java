package com.mahoutjdbcmicroservice.DataModel.Dao;

import com.mahoutjdbcmicroservice.DataModel.Domain.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    List<OrderEntity> findAll();
}
