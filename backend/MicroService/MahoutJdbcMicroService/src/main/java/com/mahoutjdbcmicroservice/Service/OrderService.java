package com.mahoutjdbcmicroservice.Service;

import com.mahoutjdbcmicroservice.DataModel.Domain.OrderEntity;

import java.util.List;

public interface OrderService {
    public List<OrderEntity> queryOrders();
}
