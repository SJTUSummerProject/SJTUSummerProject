package com.mahoutjdbcmicroservice.Service.ServiceImpl;

import com.mahoutjdbcmicroservice.DataModel.Dao.OrderRepository;
import com.mahoutjdbcmicroservice.DataModel.Domain.OrderEntity;
import com.mahoutjdbcmicroservice.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    public List<OrderEntity> queryOrders(){
        return orderRepository.findAll();
    }
}
