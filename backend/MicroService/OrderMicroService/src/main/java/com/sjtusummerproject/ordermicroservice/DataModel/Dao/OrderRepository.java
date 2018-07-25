package com.sjtusummerproject.ordermicroservice.DataModel.Dao;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity,Long>{
    public OrderEntity findById(Long id);
    public OrderEntity findFirstByUserIdAndStatus(Long userid,String status);
    public OrderEntity findFirstByStatus(String status);
}
