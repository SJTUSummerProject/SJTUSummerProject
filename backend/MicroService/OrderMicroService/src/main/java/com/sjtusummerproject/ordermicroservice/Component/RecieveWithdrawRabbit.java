package com.sjtusummerproject.ordermicroservice.Component;

import com.sjtusummerproject.ordermicroservice.Config.RabbitMQConfig;
import com.sjtusummerproject.ordermicroservice.DataModel.Dao.OrderRepository;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.ItemEntity;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.OrderEntity;
import com.sjtusummerproject.ordermicroservice.Service.TicketService;
import com.sjtusummerproject.ordermicroservice.Service.UserDetailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RecieveWithdrawRabbit {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    TicketService ticketService;
    @Autowired
    UserDetailService userDetailService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumeMessage(MultiValueMap<String,Long> message){
        System.out.println("收到退款请求");

        OrderEntity orderEntity = orderRepository.findById(message.getFirst("orderid"));

        double totalPrice = 0l;
        Set<ItemEntity> items = orderEntity.getItems();
        for(ItemEntity eachitem : items){
            if(eachitem.getStatus().equals("成功")){
                totalPrice += eachitem.getPrice()*eachitem.getNumber();
                eachitem.setStatus("失败");
                /*重新给ticket加上库存*/
                ticketService.updateStockPlus(eachitem.getTicketId(),eachitem.getNumber());
            }
        }
        /*退款给用户*/
        userDetailService.updateAccountPlus(orderEntity.getUserId(),totalPrice);

        orderEntity.setStatus("已退款");
        orderRepository.save(orderEntity);
    }
}
