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
    CartService cartService;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    AuthService authService;

    @Value("${order.page.size}")
    private int PageSize;
    @Value("${order.page.offset}")
    private int PageOffset;

    /* Get Pageable */
    Pageable createPageable(HttpServletRequest request){
        return new PageRequest(Integer.parseInt(request.getParameter("pagenumber"))-PageOffset, PageSize, new Sort(Sort.Direction.DESC, "id"));
    }

    @RequestMapping(value="/QueryByOrderid")
    public OrderEntity queryByOrderid(HttpServletResponse response, HttpServletRequest request,
                                      @RequestParam(name = "orderid") Long orderid){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = authService.callAuthService(token);
        Integer result = authService.authUser(userEntity);
        response.addHeader("errorNum", result.toString());
        if (result != 0) return null;

        return orderService.queryByOrderid(orderid);
    }

    @RequestMapping(value = "/QueryByUserid")
    @ResponseBody
    public Page<OrderEntity> queryByUserid(HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = authService.callAuthService(token);
        Integer result = authService.authUser(userEntity);
        response.addHeader("errorNum", result.toString());
        if (result != 0) return null;

        return orderService.queryByUserid(userEntity.getId(),createPageable(request));
    }

    /*在详细页面里生成订单 这个时候应该只有一个票品*/
    @RequestMapping(value = "/AddInDetailPage")
    @ResponseBody
    public OrderEntity addInDetailPage(HttpServletRequest request, HttpServletResponse response){
        Long ticketid = Long.parseLong(request.getParameter("ticketid"));
        double price = Double.parseDouble(request.getParameter("price"));
        String date = request.getParameter("date");
        Long number = Long.parseLong(request.getParameter("number"));
        String receiver = request.getParameter("receiver");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        response.addHeader("Access-Control-Expose-Headers", "errorNum");

        String token = request.getParameter("token");
        UserEntity userEntity = authService.callAuthService(token);
        Integer result = authService.authUser(userEntity);
        response.addHeader("errorNum", result.toString());
        if (result != 0) return null;

        TicketEntity ticketEntity = ticketService.queryTicketById(ticketid);

        /*此partOrder只含有orderid status("待支付") time三个基本属性*/
        OrderEntity partOrder = orderService.createBasicOrder();
        /*继续填入进阶信息 即userid receiver phone address*/
        /*最后还差 items*/
        partOrder = orderService.createAdditionOrderEntity(partOrder,userEntity,receiver,phone,address);
        ItemEntity itemEntity = orderService.createFullItemFromOrder(partOrder, ticketEntity, price, date, number);
        return orderService.saveInDetailPage(partOrder,itemEntity);
    }

    /*
     * 在购物车页面里生成订单 这个时候应该有很多票品
     * 我们的票品信息应该从 CartEntity 获得 而不是TicketEntity
     * */
    @RequestMapping(value = "/AddBatchInCart")
    @ResponseBody
    public OrderEntity addBatchInCart(HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = authService.callAuthService(token);
        Integer result = authService.authUser(userEntity);
        response.addHeader("errorNum", result.toString());
        if (result != 0) return null;

        String cartids = request.getParameter("cartids");
        String receiver = request.getParameter("receiver");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        List<CartEntity> carts = cartService.queryByBatchIds(cartids);
        try{
            for (CartEntity cartEntity : carts){
                System.out.println(cartEntity.getId());
            }
        }
        catch (Exception e){
            System.out.println("fail");
        }
        OrderEntity partOrder = orderService.createBasicOrder();
        partOrder = orderService.createAdditionOrderEntity(partOrder,userEntity,receiver,phone,address);

        return orderService.saveBatchInCart(partOrder,userEntity,carts);
    }

    /* 在订单页面 点击购买 */
    @RequestMapping(value = "/Buy")
    @ResponseBody
    public HashMap<String,Object> buyOrder(HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = authService.callAuthService(token);
        Integer result = authService.authUser(userEntity);
        response.addHeader("errorNum", result.toString());
        if (result != 0) return null;

        Long orderid = Long.parseLong(request.getParameter("orderid"));
        return orderService.buy(orderid, token);
    }

    /* 在订单页面 点击取消订单
     * 此时订单的状态一定为待发货
     * */
    @RequestMapping(value = "/Cancel")
    @ResponseBody
    public String cancelOrder(HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = authService.callAuthService(token);
        Integer result = authService.authUser(userEntity);
        response.addHeader("errorNum", result.toString());
        if (result != 0) return null;
        Long orderid = Long.parseLong(request.getParameter("orderid"));
        return orderService.cancel(orderid);
    }

    /*
    * 在订单页面 点击申请退款
    * 此时订单的状态一定为已发货／已签收
    * */
    @RequestMapping(value = "Withdraw")
    @ResponseBody
    public String withdrawOrder(@RequestParam(value = "orderid") Long orderid, HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = authService.callAuthService(token);
        Integer result = authService.authUser(userEntity);
        response.addHeader("errorNum", result.toString());
        if (result != 0) return null;
         OrderEntity orderEntity = orderService.queryByOrderid(orderid);
         return orderService.addWithdrawRabbit(orderEntity);
    }


    /*
    * 点击 删除一个票品
    * */
    @RequestMapping(value = "/DeleteOne")
    @ResponseBody
    public String deleteOne(HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = authService.callAuthService(token);
        Integer result = authService.authUser(userEntity);
        response.addHeader("errorNum", result.toString());
        if (result != 0) return null;

        Long orderid = Long.parseLong(request.getParameter("orderid"));
        return orderService.deleteOne(orderid);
    }

    /*
    * 批量删除票品
    * */
    @RequestMapping(value = "/DeleteBatch")
    @ResponseBody
    public String deleteBatch(HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = authService.callAuthService(token);
        Integer result = authService.authUser(userEntity);
        response.addHeader("errorNum", result.toString());
        if (result != 0) return null;

        String ids = request.getParameter("batchid");
        return orderService.deleteSome(ids);
    }

}
