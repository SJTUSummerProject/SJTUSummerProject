package com.sjtusummerproject.ordermicroservice.Service.ServiceImpl;

import com.sjtusummerproject.ordermicroservice.DataModel.Dao.ItemRepository;
import com.sjtusummerproject.ordermicroservice.DataModel.Dao.OrderPageRepository;
import com.sjtusummerproject.ordermicroservice.DataModel.Dao.OrderRepository;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.*;
import com.sjtusummerproject.ordermicroservice.Service.OrderService;
import com.sjtusummerproject.ordermicroservice.Service.TicketService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderPageRepository orderPageRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    TicketService ticketService;

    @Value("${order.dayInMillisec}")
    Long dayInMillisec;
    @Override
    public Page<OrderEntity> queryByUserid(Long userid, Pageable pageable) {
        return orderPageRepository.findAllByUserId(userid,pageable);
    }

    @Override
    public OrderEntity saveInDetailPage(UserEntity userEntity, TicketEntity ticketEntity, double price, String date, int number) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderid(0L);
        orderEntity.setUserId(userEntity.getId());
        orderEntity.setStatus("待付款");
        orderEntity.setOrderTime(new Date());

        //orderEntity = orderRepository.findByOrderId(orderEntity.getOrderId());
        /*如果是 one to many
        * one 插了 many 就不用再插入了
        * 但是各自都要set对方
        * */
        Set<ItemEntity> set = new HashSet<>();

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
        itemEntity.setStatus("未操作");
        set.add(itemEntity);

        orderEntity.setItems(set);
        return orderRepository.save(orderEntity);
    }

    @Override
    public OrderEntity saveBatchInCart(UserEntity userEntity, List<CartEntity> cartEntityList) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderid(0L);
        orderEntity.setUserId(userEntity.getId());
        orderEntity.setStatus("待付款");
        orderEntity.setOrderTime(new Date());

        Set<ItemEntity> set = new HashSet();

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
            itemEntity.setStatus("未操作");
            set.add(itemEntity);
        }
        orderEntity.setItems(set);
        return orderRepository.save(orderEntity);
    }

    @Override
    public String buy(Long orderid) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderid);
        Date now = new Date();
        /* 订单未支付超过24小时 */
        if((now.getTime() - orderEntity.getOrderTime().getTime())>dayInMillisec)
        {
            orderEntity.setStatus("已过期");
            setItemsStatus(orderEntity,"失败");
            orderRepository.save(orderEntity);
            return "expired";
        }
        /* 用户余额不足 */
        // Add your code here

        /* 票品数量不足 */


        /* 订单支付成功 */
        orderEntity.setStatus("待发货");
        setItemsStatus(orderEntity,"成功");
        orderRepository.save(orderEntity);
        return "ok";

    }
    @Override
    public String deleteOne(Long orderid) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderid);
        orderEntity.setStatus("已删除");
        setItemsStatus(orderEntity,"失败");
        orderRepository.save(orderEntity);
        return "ok";
    }

    @Override
    public String deleteSome(String ids) {
        String[] idSplit = ids.trim().replace("[","").replace("]","").split(",");
        for(String eachId : idSplit){
            deleteOne(Long.parseLong(eachId.trim()));
        }
        return "ok";
    }

    /******************************************************************/
    /** for test **/
    @Override
    public OrderEntity test(UserEntity userEntity, TicketEntity ticketEntity, double price, String date, int number) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderid(0L);
        orderEntity.setUserId(userEntity.getId());
        orderEntity.setStatus("待付款");
        orderEntity.setOrderTime(new Date());


        //orderRepository.save(orderEntity);

        //orderEntity = orderRepository.findByOrderId(orderEntity.getOrderId());
        /*如果是 one to many
         * many 插了 one 就不用再插入了
         * */
        Set<ItemEntity> set = new HashSet<>();

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
        itemEntity.setStatus("未操作");

        ItemEntity itemEntity1 = new ItemEntity();
        itemEntity1.setItemId(0L);
        itemEntity1.setOrderEntity(orderEntity);
        itemEntity1.setNumber(number+1);
        itemEntity1.setImage(ticketEntity.getImage());
        itemEntity1.setDate(date);
        itemEntity1.setCity(ticketEntity.getCity());
        itemEntity1.setVenue(ticketEntity.getVenue());
        itemEntity1.setPrice(price+100);
        itemEntity1.setTicketId(ticketEntity.getId());
        itemEntity1.setTitle(ticketEntity.getTitle());
        itemEntity1.setStatus("未操作");

        set.add(itemEntity1);
        set.add(itemEntity);

        orderEntity.setItems(set);
//        itemRepository.save(itemEntity);
//        itemRepository.save(itemEntity1);
        orderRepository.save(orderEntity);
        System.out.println(orderEntity.getUserId());
        System.out.println(orderEntity.getStatus());
        OrderEntity orderEntity1= orderRepository.findFirstByUserIdAndStatus(orderEntity.getUserId(),orderEntity.getStatus());
//        System.out.println(orderEntity1);
        return orderEntity1;
//        List<Long> longs = new LinkedList<>();
//        longs.add(1L);
//        longs.add(2L);
//        longs.add(3L);
//        System.out.println("String " + longs.toString());
//        String[] split = longs.toString().replace("[","").replace("]","").split(",");
//        for(String each : split){
//            System.out.println(Long.parseLong(each.trim()));
//        }
    }

    @Override
    public String test1() {
        orderRepository.delete(29L);
        return null;
    }

    @Override
    public String test2() {
        itemRepository.delete(42L);
        return null;
    }

    @Override
    public String test3() {
        OrderEntity orderEntity = orderRepository.findFirstByStatus("待付款");
        Set<ItemEntity> items = orderEntity.getItems();
        for(ItemEntity eachitem : items){
            eachitem.setStatus("成功");
        }
        orderEntity.setItems(items);
        orderRepository.save(orderEntity);
        return "ok";
    }

    /***************************************/
    /* 自定义函数 */
    public String setItemsStatus(OrderEntity order,String newStatus){
        Set<ItemEntity> items = order.getItems();
        for(ItemEntity item : items)
            item.setStatus(newStatus);
        order.setItems(items);
        return "ok";
    }
    /***************************************/
}
