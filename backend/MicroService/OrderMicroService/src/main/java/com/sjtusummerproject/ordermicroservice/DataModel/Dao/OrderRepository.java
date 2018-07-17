package com.sjtusummerproject.ordermicroservice.DataModel.Dao;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity,Long>{
}
