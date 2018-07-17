package com.sjtusummerproject.ordermicroservice.Service;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.CartEntity;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.OrderEntity;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.TicketEntity;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    public Page<OrderEntity> queryByUserid(Long userid, Pageable pageable);
    public String saveInDetailPage(UserEntity userEntity, TicketEntity ticketEntity, double price, String date, int number);
    public String saveBatchInCart(UserEntity userEntity, List<CartEntity> cartEntityList);
}
