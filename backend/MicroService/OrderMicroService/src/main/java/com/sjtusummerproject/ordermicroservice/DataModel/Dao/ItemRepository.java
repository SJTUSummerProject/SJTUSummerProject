package com.sjtusummerproject.ordermicroservice.DataModel.Dao;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.ItemEntity;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<ItemEntity,Long>{

}
