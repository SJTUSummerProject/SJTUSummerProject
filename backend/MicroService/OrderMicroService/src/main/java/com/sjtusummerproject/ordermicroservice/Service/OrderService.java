package com.sjtusummerproject.ordermicroservice.Service;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    public Page<OrderEntity> queryByUserid(Long userid, Pageable pageable);
}
