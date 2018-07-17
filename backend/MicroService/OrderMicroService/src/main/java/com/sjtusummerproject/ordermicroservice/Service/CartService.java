package com.sjtusummerproject.ordermicroservice.Service;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.CartEntity;

import java.util.List;

public interface CartService {
    public CartEntity queryById(Long id);
    public List<CartEntity> queryByBatchIds(String cartids);
}
