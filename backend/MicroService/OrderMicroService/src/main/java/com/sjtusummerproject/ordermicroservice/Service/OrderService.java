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
    public OrderEntity saveInDetailPage(UserEntity userEntity, TicketEntity ticketEntity, double price, String date, int number);
    public OrderEntity saveBatchInCart(UserEntity userEntity, List<CartEntity> cartEntityList);
    public String buy(Long orderid);
    public String deleteOne(Long orderid);
    public String deleteSome(String ids);
    /* test */
    public OrderEntity test(UserEntity userEntity, TicketEntity ticketEntity, double price, String date, int number);
    public String test1();
    public String test2();
    public String test3();
}
