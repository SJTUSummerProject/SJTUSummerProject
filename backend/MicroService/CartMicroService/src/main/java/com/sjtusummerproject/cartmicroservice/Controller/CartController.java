package com.sjtusummerproject.cartmicroservice.Controller;

import com.sjtusummerproject.cartmicroservice.DataModel.Domain.CartEntity;
import com.sjtusummerproject.cartmicroservice.DataModel.Domain.TicketEntity;
import com.sjtusummerproject.cartmicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.cartmicroservice.Service.CartService;
import com.sjtusummerproject.cartmicroservice.Service.TicketService;
import com.sjtusummerproject.cartmicroservice.Service.UserService;
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
@RequestMapping(value = "/Cart")
public class CartController {
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

    /* "/SaveInDetailPage" 包括了更新与插入 */
    @RequestMapping(value = "/SaveInDetailPage")
    @ResponseBody
    public String saveInDetailPage(HttpServletRequest request, HttpServletResponse response){
        /* parameters */
        Long ticketId = Long.parseLong(request.getParameter("ticketid"));
        Long userId = Long.parseLong(request.getParameter("userid"));
        double price = Double.parseDouble(request.getParameter("price"));
        String date = request.getParameter("date");
        int number = Integer.parseInt(request.getParameter("number"));

        TicketEntity ticketEntity = ticketService.queryTicketById(ticketId);
        UserEntity userEntity = userService.queryById(userId);
        cartService.saveInDetailPageByMultiInfo(userEntity,ticketEntity,price,date,number);
        return "ok";
    }

    /* 在购物车点击 + 按钮，点一次触发一次 */
    @RequestMapping(value = "/NumberPlusOneInCart")
    @ResponseBody
    public String numberPlusOneInCart(HttpServletRequest request, HttpServletResponse response){
        /* parameters */
        Long entryId = Long.parseLong(request.getParameter("entryid"));
        cartService.numberPlusOneInCartById(entryId);
        return "ok";
    }

    /* 在购物车点击 - 按钮，点一次触发一次 */
    @RequestMapping(value = "/NumberMinusOneInCart")
    @ResponseBody
    public String numberMinusOneInCart(HttpServletRequest request,HttpServletResponse response){
        /* parameters */
        Long entryId = Long.parseLong(request.getParameter("entryid"));

        cartService.numberMinusOneInCartById(entryId);
        return "ok";
    }

    /* 在购物车 "编辑 <数量>"，编辑完成触发 */
    @RequestMapping(value = "/NumberEditInCart")
    @ResponseBody
    public String numberPlusSomeInCart(HttpServletRequest request, HttpServletResponse response){
        /* parameters */
        Long entryId = Long.parseLong(request.getParameter("entryid"));
        int number = Integer.parseInt(request.getParameter("number"));

        cartService.numberEditInCartById(entryId,number);
        return "ok";
    }

    /* 在购物车 "删除 <物品>"，点击删除触发 */
    @RequestMapping(value = "/DeleteInCart")
    @ResponseBody
    public String deleteIncart(HttpServletRequest request, HttpServletResponse response){
        /* parameters */
        Long entryId = Long.parseLong(request.getParameter("entryid"));

        cartService.deleteInCartById(entryId);
        return "ok";
    }

    @RequestMapping(value = "/DeleteBatchInCart")
    @ResponseBody
    public String deleteBatchInCart(HttpServletRequest request, HttpServletResponse response){
        /* parameters */
        String entryIds = request.getParameter("batchentryid");

        cartService.deleteBatchInCartByIds(entryIds);
        return "ok";
    }

    /* 访问购物车界面 即触发 */
    /* 有page! */
    @RequestMapping(value = "/QueryByUserid")
    @ResponseBody
    public Page<CartEntity> queryByUserid(HttpServletRequest request, HttpServletResponse response){
        /* parameters */
        Long userId = Long.parseLong(request.getParameter("userid"));

        return cartService.findInCartByUserid(userId,createPageable(request));
    }

    @RequestMapping(value = "/QueryById")
    @ResponseBody
    public CartEntity queryById(HttpServletRequest request, HttpServletResponse response){
        Long cartid = Long.parseLong(request.getParameter("cartid"));
        return cartService.queryById(cartid);
    }

    @RequestMapping(value = "/QueryBatchByIds")
    @ResponseBody
    public List<CartEntity> queryBatchByIds(HttpServletRequest request, HttpServletResponse response){
        String ids = request.getParameter("batchid");
        return cartService.queryByBatchId(ids);
    }
}
