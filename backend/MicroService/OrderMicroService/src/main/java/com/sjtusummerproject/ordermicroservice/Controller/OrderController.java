package com.sjtusummerproject.ordermicroservice.Controller;

import com.sjtusummerproject.ordermicroservice.DataModel.Dao.OrderRepository;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.*;
import com.sjtusummerproject.ordermicroservice.Service.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/Order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    TicketService ticketService;
    @Autowired
    UserService userService;
    @Autowired
    CartService cartService;
    @Autowired
    UserDetailService userDetailService;

    @Value("${cart.page.size}")
    private int PageSize;
    @Value("${cart.page.offset}")
    private int PageOffset;

    /* Get Pageable */
    Pageable createPageable(HttpServletRequest request){
        return new PageRequest(Integer.parseInt(request.getParameter("pagenumber"))-PageOffset, PageSize, new Sort(Sort.Direction.DESC, "id"));
    }

    @RequestMapping(value = "/QueryByUserid")
    @ResponseBody
    public Page<OrderEntity> queryByUserid(HttpServletRequest request, HttpServletResponse response){
        Long userid = Long.parseLong(request.getParameter("userid"));

        return orderService.queryByUserid(userid,createPageable(request));
    }

    /*在详细页面里生成订单 这个时候应该只有一个票品*/
    @RequestMapping(value = "/AddInDetailPage")
    @ResponseBody
    public OrderEntity addInDetailPage(HttpServletRequest request, HttpServletResponse response){
        Long userid = Long.parseLong(request.getParameter("userid"));
        Long ticketid = Long.parseLong(request.getParameter("ticketid"));
        double price = Double.parseDouble(request.getParameter("price"));
        String date = request.getParameter("date");
        Long number = Long.parseLong(request.getParameter("number"));
        String receiver = request.getParameter("receiver");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        TicketEntity ticketEntity = ticketService.queryTicketById(ticketid);
        UserEntity userEntity = userService.queryById(userid);
        UserDetailEntity userDetailEntity = userDetailService.queryUserDetailById(userid);

        /*此partOrder只含有orderid status("待支付") time三个基本属性*/
        OrderEntity partOrder = orderService.createBasicOrder();
        /*继续填入进阶信息 即userid receiver phone address*/
        /*最后还差 items*/
        partOrder = orderService.createAdditionOrderEntity(partOrder,userEntity,userDetailEntity,receiver,phone,address);
        ItemEntity itemEntity = orderService.createFullItemFromOrder(partOrder, ticketEntity, price, date, number);
        return orderService.saveInDetailPage(partOrder,itemEntity);
    }

    /* 在详细页面/购物车/订单页面 点击购买 */
    @RequestMapping(value = "/Buy")
    @ResponseBody
    public HashMap<String,Object> buyOrder(HttpServletRequest request, HttpServletResponse response){
        Long orderid = Long.parseLong(request.getParameter("orderid"));
        return orderService.buy(orderid);
    }

    /* 在订单页面 点击取消订单
     * 此时订单的状态一定为待发货
     * */
    @RequestMapping(value = "/Cancel")
    @ResponseBody
    public String cancelOrder(HttpServletRequest request, HttpServletResponse response){
        Long orderid = Long.parseLong(request.getParameter("orderid"));
        return orderService.cancel(orderid);
    }

    /*
    * 在订单页面 点击申请退款
    * 此时订单的状态一定为已发货／已签收
    * */
    @RequestMapping(value = "Withdraw")
    @ResponseBody
    public String withdrawOrder(@RequestParam(value = "orderid") Long orderid){
         OrderEntity orderEntity = orderService.queryByOrderid(orderid);
         return orderService.addWithdrawRabbit(orderEntity);
    }

    /*
    * 在购物车页面里生成订单 这个时候应该有很多票品
    * 我们的票品信息应该从 CartEntity 获得 而不是TicketEntity
    * */
    @RequestMapping(value = "/AddBatchInCart")
    @ResponseBody
    public OrderEntity addBatchInCart(HttpServletRequest request, HttpServletResponse response){
        String cartids = request.getParameter("cartids");
        Long userid = Long.parseLong(request.getParameter("userid"));

        String receiver = request.getParameter("receiver");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        List<CartEntity> carts = cartService.queryByBatchIds(cartids);
        UserEntity userEntity = userService.queryById(userid);
        UserDetailEntity userDetailEntity = userDetailService.queryUserDetailById(userid);
        /*经过一下两步 partorder 只差items列表了，需要从carts中拿到items*/
        OrderEntity partOrder = orderService.createBasicOrder();
        partOrder = orderService.createAdditionOrderEntity(partOrder,userEntity,userDetailEntity,receiver,phone,address);

        return orderService.saveBatchInCart(partOrder,userEntity,carts);
    }

    /*
    * 点击 删除一个票品
    * */
    @RequestMapping(value = "/DeleteOne")
    @ResponseBody
    public String deleteOne(HttpServletRequest request, HttpServletResponse response){
        Long orderid = Long.parseLong(request.getParameter("orderid"));

        return orderService.deleteOne(orderid);
    }

    /*
    * 批量删除票品
    * */
    @RequestMapping(value = "/DeleteBatch")
    @ResponseBody
    public String deleteBatch(HttpServletRequest request, HttpServletResponse response){
        String ids = request.getParameter("batchid");

        return orderService.deleteSome(ids);
    }

    /**************************************************************/
    /** for test **/
    @RequestMapping(value = "/test")
    @ResponseBody
    public OrderEntity test(HttpServletRequest request, HttpServletResponse response){
        UserEntity userEntity = new UserEntity();
        userEntity.setAuthority("Admin");
        userEntity.setEmail("2286455782@qq.com");
        userEntity.setId(5L);
        userEntity.setPassword("xtqxtq");
        userEntity.setUsername("sky");
        userEntity.setStatus("Active");

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setCity("上海");
        ticketEntity.setDates("2018-07-17");
        ticketEntity.setEndDate(new Date());
        ticketEntity.setStartDate(new Date());
        ticketEntity.setHighprice(1000);
        ticketEntity.setLowprice(300);
        ticketEntity.setImage("http://image4.xishiqu.cn/upload/activity/118/070/20180705018/v/b/7CF0898C-A18F-C828-661D-86804C7232B4.jpg");
        ticketEntity.setIntro("7月5日15:17原价预售，数量有限，每个身份证限购1张。");
        ticketEntity.setStock(1000L);
        ticketEntity.setId(50L);
        ticketEntity.setTime("2018-07-17 20:00");
        ticketEntity.setType("vocal concert");
        ticketEntity.setTitle("预订 薛之谦演唱会—上海站");
        ticketEntity.setVenue("上海虹口足球场（上海市东江湾路444号）");

        double price = 580;
        String date = "2018-07-17 21:00";
        Long number = 5l;

        OrderEntity res = orderService.test(userEntity,ticketEntity,price,date,number);
        return res;
    }

    @RequestMapping(value = "/test1")
    @ResponseBody
    public String test1(HttpServletRequest request, HttpServletResponse response){
        orderService.test1();
        return "ok";
    }

    @RequestMapping(value = "/test2")
    @ResponseBody
    public String test2(HttpServletRequest request, HttpServletResponse response){
        orderService.test2();
        return "ok";
    }

    @RequestMapping(value = "/test3")
    @ResponseBody
    public String test3(HttpServletRequest request, HttpServletResponse response){
        return orderService.test3();
    }

    @RequestMapping(value = "/test4")
    @ResponseBody
    public String test4(HttpServletRequest request, HttpServletResponse response){
        String hey = request.getParameter("hey");
        if(hey == null){
            System.out.println("hey is null!");
        }
        else if(hey.equals("")){
            System.out.println("hey equals \"\"");
        }
        System.out.println("not null");
        return "ok";
    }
}
