package com.sjtusummerproject.ordermicroservice.DataModel.Dao;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderPageRepository extends PagingAndSortingRepository<OrderEntity, Long> {
    public Page<OrderEntity> findAllByUserId(Long userid, Pageable pageable);
    public Page<OrderEntity> findAllByUserIdAndStatusNotLike(Long userid,String status, Pageable pageable);
}
