package com.sjtusummerproject.ordermicroservice.Service.ServiceImpl;

import com.sjtusummerproject.ordermicroservice.DataModel.Dao.OrderPageRepository;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.OrderEntity;
import com.sjtusummerproject.ordermicroservice.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderPageRepository orderPageRepository;

    @Override
    public Page<OrderEntity> queryByUserid(Long userid, Pageable pageable) {
        return orderPageRepository.findAllByUserId(userid,pageable);
    }
}
