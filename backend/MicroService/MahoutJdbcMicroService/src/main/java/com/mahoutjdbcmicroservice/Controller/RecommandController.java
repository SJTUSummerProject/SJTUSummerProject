package com.mahoutjdbcmicroservice.Controller;

import com.mahoutjdbcmicroservice.Service.UserRecommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value = "/Recommand")
public class RecommandController {

    @Autowired
    UserRecommandService userRecommandService;


    //一次最大返回六个
    @RequestMapping(value = "/QueryRecommandTicket")
    @ResponseBody
    public List<Long> queryRecommandTicket(@RequestParam(name = "userid") Long userid){
        List<Long> list = userRecommandService.queryTicketByUserid(userid);
        if(list == null)
            return new LinkedList<>();
        return list;
    }
}
