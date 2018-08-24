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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
    RestTemplate restTemplate = new RestTemplate();

    @Value("${cart.page.size}")
    private int PageSize;
    @Value("${cart.page.offset}")
    private int PageOffset;

    @Value("${authservice.url}")
    private String url;

    /* Get Pageable */
    Pageable createPageable(HttpServletRequest request){
        return new PageRequest(Integer.parseInt(request.getParameter("pagenumber"))-PageOffset, PageSize, new Sort(Sort.Direction.DESC, "id"));
    }

    /* "/SaveInDetailPage" 包括了更新与插入 */
    @RequestMapping(value = "/SaveInDetailPage")
    @ResponseBody
    public String saveInDetailPage(HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = callAuthService(token);
        Integer result = authUser(userEntity);
        response.addHeader("errorNum", ((Integer) result).toString());
        if (result != 0) return null;
        Long ticketId = Long.parseLong(request.getParameter("ticketid"));
        double price = Double.parseDouble(request.getParameter("price"));
        String date = request.getParameter("date");
        Long number = Long.parseLong(request.getParameter("number"));

        TicketEntity ticketEntity = ticketService.queryTicketById(ticketId);
        cartService.saveInDetailPageByMultiInfo(userEntity,ticketEntity,price,date,number);
        return "ok";
    }

    /* 在购物车 "编辑 <数量>"，编辑完成触发 */
    @RequestMapping(value = "/NumberEditInCart")
    @ResponseBody
    public String numberPlusSomeInCart(HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = callAuthService(token);
        Integer result = authUser(userEntity);
        response.addHeader("errorNum", ((Integer) result).toString());
        if (result != 0) return null;
        /* parameters */
        Long entryId = Long.parseLong(request.getParameter("entryid"));

        Long number = Long.parseLong(request.getParameter("number"));
        cartService.numberEditInCartById(entryId,number);
        return "ok";
    }

    @RequestMapping(value = "/DeleteBatchInCart")
    @ResponseBody
    public String deleteBatchInCart(HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = callAuthService(token);
        Integer result = authUser(userEntity);
        response.addHeader("errorNum", ((Integer) result).toString());
        if (result != 0) return null;
        /* parameters */
        String entryIds = request.getParameter("batchentryid");
        cartService.deleteBatchInCartByIds(entryIds);
        return "ok";
    }

    /* 访问购物车界面 即触发 */
    /* 有page! */
    @RequestMapping(value = "/QueryByUserId")
    @ResponseBody
    public Page<CartEntity> queryByUserid(HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Access-Control-Expose-Headers", "errorNum");
        String token = request.getParameter("token");
        UserEntity userEntity = callAuthService(token);
        Integer result = authUser(userEntity);
        response.addHeader("errorNum", ((Integer) result).toString());
        if (result != 0) return null;

        /* parameters */
        Long userId = userEntity.getId();
        return cartService.findInCartByUserid(userId,createPageable(request));
    }

    @RequestMapping(value = "/QueryBatchByIds")
    @ResponseBody
    public List<CartEntity> queryBatchByIds(HttpServletRequest request, HttpServletResponse response){
        /* parameters*/
        String ids = request.getParameter("batchid");
        return cartService.queryByBatchId(ids);
    }

    private UserEntity callAuthService(String token){
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("token", token);
        return restTemplate.postForObject(url, multiValueMap, UserEntity.class);
    }

    private int authUser(UserEntity userEntity){

        if (userEntity == null) return 1;
        else if (!userEntity.getAuthority().equals("Customer")) return 2;
        else if (userEntity.getStatus().equals("Frozen")) return 3;
        else return 0;
    }
}
