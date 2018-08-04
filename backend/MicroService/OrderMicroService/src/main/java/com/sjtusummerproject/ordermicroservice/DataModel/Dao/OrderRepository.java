package com.sjtusummerproject.ordermicroservice.DataModel.Dao;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.OrderEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<OrderEntity,Long>{
    public OrderEntity findById(Long id);
    public OrderEntity findFirstByUserIdAndStatus(Long userid,String status);
    public OrderEntity findFirstByStatus(String status);
    public List<OrderEntity> findAll();
}
