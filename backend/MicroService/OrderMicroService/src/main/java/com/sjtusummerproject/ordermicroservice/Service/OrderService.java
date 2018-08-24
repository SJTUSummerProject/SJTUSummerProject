package com.sjtusummerproject.ordermicroservice.Service;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

public interface OrderService {
    public Page<OrderEntity> queryByUserid(Long userid, Pageable pageable);
    public OrderEntity queryByOrderid(Long orderid);
    public OrderEntity saveInDetailPage(OrderEntity partOrderEntity, ItemEntity itemEntity);
    public OrderEntity saveBatchInCart(OrderEntity partOrder,UserEntity userEntity, List<CartEntity> cartEntityList);
    public HashMap buy(Long orderid, String token);
    public String cancel(Long orderid);
    public String deleteOne(Long orderid);
    public String deleteSome(String ids);
    public String addWithdrawRabbit(OrderEntity orderEntity);

    public OrderEntity createBasicOrder();
    public OrderEntity createAdditionOrderEntity(OrderEntity orderEntity, UserEntity userEntity,String receiver,String phone,String address );
    public ItemEntity createFullItemFromOrder(OrderEntity orderEntity, TicketEntity ticketEntity,double price, String date, Long number);
    public ItemEntity createFullItemFromCartAndOrder(CartEntity cartEntity, OrderEntity orderEntity);
}
