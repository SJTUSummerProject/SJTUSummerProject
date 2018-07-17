package com.sjtusummerproject.ordermicroservice.Controller;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.OrderEntity;
import com.sjtusummerproject.ordermicroservice.Service.OrderService;
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

@RestController
@RequestMapping(value = "/Order")
public class OrderController {
    @Autowired
    OrderService orderService;

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


        return null;
    }
}
