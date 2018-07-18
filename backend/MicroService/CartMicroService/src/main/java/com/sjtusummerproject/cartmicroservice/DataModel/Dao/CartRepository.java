package com.sjtusummerproject.cartmicroservice.DataModel.Dao;


import com.sjtusummerproject.cartmicroservice.DataModel.Domain.CartEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartRepository extends CrudRepository<CartEntity,Long> {
    public CartEntity findByUserIdAndTicketIdAndDateAndAndPrice(Long userId,Long ticketId,String date,double price);
    public CartEntity findById(Long id);
    public void deleteById(Long id);
    public List<CartEntity> findAllByUserId(Long userid);
}
