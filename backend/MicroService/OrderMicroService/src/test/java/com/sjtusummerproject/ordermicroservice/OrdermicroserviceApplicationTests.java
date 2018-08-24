package com.sjtusummerproject.ordermicroservice;

import com.sjtusummerproject.ordermicroservice.DataModel.Dao.ItemRepository;
import com.sjtusummerproject.ordermicroservice.DataModel.Dao.OrderRepository;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.ItemEntity;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.OrderEntity;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.TicketEntity;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdermicroserviceApplicationTests {

	@Autowired
	OrderRepository orderRepository;
	@Autowired
	ItemRepository itemRepository;
	@Test
	public void contextLoads() {

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


		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setUserId(userEntity.getId());
		orderEntity.setStatus("待付款");
		orderEntity.setOrderTime(new Date());
		try {
			orderRepository.save(orderEntity);
		}
		catch (Exception e){
			System.out.println("1 fail");
		}
		ItemEntity itemEntity = new ItemEntity();
		itemEntity.setId(3L);
		itemEntity.setOrderEntity(orderEntity);
		itemEntity.setImage(ticketEntity.getImage());
		itemEntity.setDate(date);
		itemEntity.setCity(ticketEntity.getCity());
		itemEntity.setVenue(ticketEntity.getVenue());
		itemEntity.setPrice(price);
		itemEntity.setTicketId(ticketEntity.getId());
		itemEntity.setTitle(ticketEntity.getTitle());
		try {
			itemRepository.save(itemEntity);
		}
		catch (Exception e) {
			System.out.println("2 fail");
		}
	}
}
