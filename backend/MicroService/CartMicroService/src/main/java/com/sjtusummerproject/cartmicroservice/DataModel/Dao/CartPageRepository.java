package com.sjtusummerproject.cartmicroservice.DataModel.Dao;

import com.sjtusummerproject.cartmicroservice.DataModel.Domain.CartEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.Date;


public interface CartPageRepository extends PagingAndSortingRepository<CartEntity, Long> {
    public Page<CartEntity> findAllByUserId(Long id,Pageable pageable);
}