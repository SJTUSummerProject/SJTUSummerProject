package com.sjtusummerproject.ordermicroservice.Service.ServiceImpl;

import com.sjtusummerproject.ordermicroservice.DataModel.Dao.ItemRepository;
import com.sjtusummerproject.ordermicroservice.DataModel.Dao.OrderPageRepository;
import com.sjtusummerproject.ordermicroservice.DataModel.Dao.OrderRepository;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.*;
import com.sjtusummerproject.ordermicroservice.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderPageRepository orderPageRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;

    @Override
    public Page<OrderEntity> queryByUserid(Long userid, Pageable pageable) {
        return orderPageRepository.findAllByUserId(userid,pageable);
    }

    @Override
    public String saveInDetailPage(UserEntity userEntity, TicketEntity ticketEntity, double price, String date, int number) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderid(0L);
        orderEntity.setUserId(userEntity.getId());
        orderEntity.setStatus("待付款");
        orderEntity.setOrderTime(new Date());
        orderRepository.save(orderEntity);

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setItemId(0L);
        itemEntity.setOrderEntity(orderEntity);
        itemEntity.setNumber(number);
        itemEntity.setImage(ticketEntity.getImage());
        itemEntity.setDate(date);
        itemEntity.setCity(ticketEntity.getCity());
        itemEntity.setVenue(ticketEntity.getVenue());
        itemEntity.setPrice(price);
        itemEntity.setTicketId(ticketEntity.getId());
        itemEntity.setTitle(ticketEntity.getTitle());

        itemRepository.save(itemEntity);

        return "ok";
    }

    @Override
    public String saveBatchInCart(UserEntity userEntity, List<CartEntity> cartEntityList) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderid(0L);
        orderEntity.setUserId(userEntity.getId());
        orderEntity.setStatus("待付款");
        orderEntity.setOrderTime(new Date());
        orderRepository.save(orderEntity);

        for(CartEntity eachCart : cartEntityList){
            ItemEntity itemEntity = new ItemEntity();
            itemEntity.setItemId(0L);
            itemEntity.setOrderEntity(orderEntity);
            itemEntity.setNumber(eachCart.getNumber());
            itemEntity.setImage(eachCart.getImage());
            itemEntity.setDate(eachCart.getDate());
            itemEntity.setCity(eachCart.getCity());
            itemEntity.setVenue(eachCart.getVenue());
            itemEntity.setPrice(eachCart.getPrice());
            itemEntity.setTicketId(eachCart.getTicketId());
            itemEntity.setTitle(eachCart.getTitle());
            itemRepository.save(itemEntity);
        }
        return "ok";
    }
}
