package com.sjtusummerproject.ordermicroservice.ServiceTest;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.TicketEntity;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.ordermicroservice.Service.CartService;
import com.sjtusummerproject.ordermicroservice.Service.OrderService;
import com.sjtusummerproject.ordermicroservice.Service.ServiceImpl.OrderServiceImpl;
import org.junit.Test;

import java.util.Date;

public class TestOrderService {

    public static void main(String[] args) {

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
        int number = 5;
    }
}
