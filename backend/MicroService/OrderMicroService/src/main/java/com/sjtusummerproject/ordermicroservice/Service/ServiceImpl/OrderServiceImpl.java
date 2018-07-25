package com.sjtusummerproject.ordermicroservice.Service.ServiceImpl;

import com.sjtusummerproject.ordermicroservice.Config.RabbitMQConfig;
import com.sjtusummerproject.ordermicroservice.DataModel.Dao.ItemRepository;
import com.sjtusummerproject.ordermicroservice.DataModel.Dao.OrderPageRepository;
import com.sjtusummerproject.ordermicroservice.DataModel.Dao.OrderRepository;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.*;
import com.sjtusummerproject.ordermicroservice.Service.OrderService;
import com.sjtusummerproject.ordermicroservice.Service.TicketService;
import com.sjtusummerproject.ordermicroservice.Service.UserDetailService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${order.dayInMillisec}")
    Long dayInMillisec;
    @Override
    public Page<OrderEntity> queryByUserid(Long userid, Pageable pageable) {
        return orderPageRepository.findAllByUserIdAndStatusNotLike(userid,"已删除",pageable);
    }

    @Override
    public OrderEntity queryByOrderid(Long orderid) {
        return orderRepository.findById(orderid);
    }

    @Override
    public OrderEntity saveInDetailPage(OrderEntity partOrderEntity, ItemEntity itemEntity) {
        /*如果是 one to many
        * one 插了 many 就不用再插入了
        * 但是各自都要set对方
        * */
        Set<ItemEntity> set = new HashSet<>();
        set.add(itemEntity);

        partOrderEntity.setItems(set);
        return orderRepository.save(partOrderEntity);
    }

    @Override
    public OrderEntity saveBatchInCart(OrderEntity partOrder, UserEntity userEntity, List<CartEntity> cartEntityList) {
        Set<ItemEntity> set = new HashSet();

        for(CartEntity eachCart : cartEntityList){
            ItemEntity itemEntity = createFullItemFromCartAndOrder(eachCart,partOrder);
            set.add(itemEntity);
        }

        partOrder.setItems(set);
        return orderRepository.save(partOrder);
    }

    @Override
    public HashMap<String,Object> buy(Long orderid, String token) {
        OrderEntity orderEntity = orderRepository.findById(orderid);
        Date now = new Date();
        HashMap<String,Object> res = new HashMap<>();
        /* 订单未支付超过24小时 */
        if((now.getTime() - orderEntity.getOrderTime().getTime())>dayInMillisec)
        {
            orderEntity.setStatus("已过期");
            setItemsStatus(orderEntity,"失败");
            orderRepository.save(orderEntity);
            res.put("message","expired");
            return res;
        }

        double totalPrice = 0L;
        for(ItemEntity eachitem : orderEntity.getItems()){
            totalPrice += eachitem.getPrice()*eachitem.getNumber();
        }

        /* 用户余额不足 */
        UserDetailEntity userDetail = userDetailService.queryUserDetailById(token);
        if(userDetail.getAccount()<totalPrice){
            res.put("message","Insufficient balance");
            return res;
        }

        double succPrice = 0L;
        /* 票品数量不足 */
        Set<ItemEntity> items = orderEntity.getItems();
        List<ItemEntity> failItems = new LinkedList<>();
        for(ItemEntity eachItem : items){
            /*
            * 如果库存够 会减去getNumber
            * 如果不够 就不会做任何事情
            *false 表示失败
            * */
            if(!ticketService.updateStockMinus(eachItem.getTicketId(),eachItem.getNumber())){
                eachItem.setStatus("失败");
                failItems.add(eachItem);
                continue;
            }
            succPrice += eachItem.getPrice()*eachItem.getNumber();
            eachItem.setStatus("成功");
        }

        /* 订单支付成功 */
        orderEntity.setStatus("待发货");
        orderRepository.save(orderEntity);
        res.put("message","success");
        res.put("Inventory shortage",failItems);
        /*减去succPrice*/
        userDetailService.updateAccountMinus(orderEntity.getUserId(),succPrice);
        return res;
    }

    @Override
    public String cancel(Long orderid) {
        OrderEntity orderEntity = orderRepository.findById(orderid);
        double totalPrice = 0l;
        Set<ItemEntity> items = orderEntity.getItems();
        for(ItemEntity eachitem : items){
            if(eachitem.getStatus().equals("成功")){
                totalPrice += eachitem.getPrice()*eachitem.getNumber();
                eachitem.setStatus("失败");
                ticketService.updateStockPlus(eachitem.getTicketId(),eachitem.getNumber());
            }
        }
        userDetailService.updateAccountPlus(orderEntity.getUserId(),totalPrice);

        orderEntity.setStatus("已取消");
        orderRepository.save(orderEntity);
        return "ok";
    }

    /*发送 申请退款 的消息*/
    @Override
    public String addWithdrawRabbit(OrderEntity orderEntity) {
        Date now = new Date();

        if((now.getTime() - orderEntity.getOrderTime().getTime()>7*dayInMillisec))
        {
            return "已超过7天 不能退款";
        }

        orderEntity.setStatus("退款中");
        orderRepository.save(orderEntity);
        MultiValueMap<String,Long> message = new LinkedMultiValueMap<>();
        message.add("orderid",orderEntity.getId());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message);
        return "ok";
    }

    @Override
    public String deleteOne(Long orderid) {
        OrderEntity orderEntity = orderRepository.findById(orderid);
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
    public OrderEntity test(UserEntity userEntity, TicketEntity ticketEntity, double price, String date, Long number) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(0L);
        orderEntity.setUserId(userEntity.getId());
        orderEntity.setStatus("待付款");
        orderEntity.setOrderTime(new Date());


        //orderRepository.save(orderEntity);

        //orderEntity = orderRepository.findById(orderEntity.getId());
        /*如果是 one to many
         * many 插了 one 就不用再插入了
         * */
        Set<ItemEntity> set = new HashSet<>();

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(0L);
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
        itemEntity1.setId(0L);
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

    public String test1() {
        orderRepository.delete(29L);
        return null;
    }

    public String test2() {
        itemRepository.delete(42L);
        return null;
    }

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

    /*
    * 只包含order的基本信息
    * orderid status ordertime
    * */
    @Override
    public OrderEntity createBasicOrder() {
        OrderEntity partOrder = new OrderEntity();
        partOrder.setId(0L);
        partOrder.setStatus("待付款");
        partOrder.setOrderTime(new Date());
        return partOrder;
    }

    /*填入order 进阶信息 即userid receiver phone address*/
    public OrderEntity createAdditionOrderEntity(OrderEntity orderEntity, UserEntity userEntity,String receiver,String phone,String address ){
        orderEntity.setUserId(userEntity.getId());
        orderEntity.setReceiver(receiver);
        orderEntity.setPhone(phone);
        orderEntity.setAddress(address);
        return orderEntity;
    }

    /*
    * 创建了一个完整的item entity
    * 并 填入了order entity
    * */
    @Override
    public ItemEntity createFullItemFromOrder(OrderEntity orderEntity, TicketEntity ticketEntity,double price, String date, Long number){
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(0L);
        itemEntity.setStatus("未操作");

        itemEntity.setImage(ticketEntity.getImage());
        itemEntity.setCity(ticketEntity.getCity());
        itemEntity.setVenue(ticketEntity.getVenue());
        itemEntity.setTicketId(ticketEntity.getId());
        itemEntity.setTitle(ticketEntity.getTitle());

        itemEntity.setOrderEntity(orderEntity);

        itemEntity.setPrice(price);
        itemEntity.setDate(date);
        itemEntity.setNumber(number);

        return itemEntity;
    }

    @Override
    public ItemEntity createFullItemFromCartAndOrder(CartEntity cartEntity, OrderEntity orderEntity) {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(0L);
        itemEntity.setStatus("未操作");

        itemEntity.setNumber(cartEntity.getNumber());
        itemEntity.setImage(cartEntity.getImage());
        itemEntity.setDate(cartEntity.getDate());
        itemEntity.setCity(cartEntity.getCity());
        itemEntity.setVenue(cartEntity.getVenue());
        itemEntity.setPrice(cartEntity.getPrice());
        itemEntity.setTicketId(cartEntity.getTicketId());
        itemEntity.setTitle(cartEntity.getTitle());

        itemEntity.setOrderEntity(orderEntity);

        return itemEntity;
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
