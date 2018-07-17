package com.sjtusummerproject.ordermicroservice.Controller;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.CartEntity;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.OrderEntity;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.TicketEntity;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.ordermicroservice.Service.CartService;
import com.sjtusummerproject.ordermicroservice.Service.OrderService;
import com.sjtusummerproject.ordermicroservice.Service.TicketService;
import com.sjtusummerproject.ordermicroservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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


    @Value("${cart.page.size}")
    private int PageSize;
    @Value("${cart.page.offset}")
    private int PageOffset;

    /* Get Pageable */
    Pageable createPageable(HttpServletRequest request){
        return new PageRequest(Integer.parseInt(request.getParameter("pagenumber"))-PageOffset, PageSize, new Sort(Sort.Direction.ASC, "id"));
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
    public String addInDetailPage(HttpServletRequest request, HttpServletResponse response){
        Long userid = Long.parseLong(request.getParameter("userid"));
        Long ticketid = Long.parseLong(request.getParameter("ticketid"));
        double price = Double.parseDouble(request.getParameter("price"));
        String date = request.getParameter("date");
        int number = Integer.parseInt(request.getParameter("number"));

        TicketEntity ticketEntity = ticketService.queryTicketById(ticketid);
        UserEntity userEntity = userService.queryById(userid);
        return orderService.saveInDetailPage(userEntity,ticketEntity,price,date,number);
    }

    /*
    * 在购物车页面里生成订单 这个时候应该有很多票品
    * 我们的票品信息应该从 CartEntity 获得 而不是TicketEntity
    * */
    @RequestMapping(value = "/AddBatchInCart")
    @ResponseBody
    public String addBatchInCart(HttpServletRequest request, HttpServletResponse response){
        String cartids = request.getParameter("cartids");
        Long userid = Long.parseLong(request.getParameter("userid"));

        List<CartEntity> carts = cartService.queryByBatchIds(cartids);
        UserEntity userEntity = userService.queryById(userid);

        return orderService.saveBatchInCart(userEntity,carts);
    }


}
