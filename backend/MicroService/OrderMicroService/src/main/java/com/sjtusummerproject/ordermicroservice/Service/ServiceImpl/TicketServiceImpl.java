package com.sjtusummerproject.ordermicroservice.Service.ServiceImpl;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.TicketEntity;
import com.sjtusummerproject.ordermicroservice.Service.TicketService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TicketServiceImpl implements TicketService{
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    String baseUrl="http://ticket-microservice:8080";

    @Override
    public TicketEntity queryTicketById(Long id) {
        /* 发送给 TicketMicroService */
        String url=baseUrl+"/Ticket/QueryById?"+"id="+id;
        /* 注意：必须 http、https……开头，不然报错，浏览器地址栏不加 http 之类不出错是因为浏览器自动帮你补全了 */
        RestTemplate template = new RestTemplate();
        TicketEntity result = template.getForObject(url, TicketEntity.class);

        System.out.println("the result in query ticket "+result);
        return result;
    }
}
