package com.sjtusummerproject.ordermicroservice.Service.ServiceImpl;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.TicketEntity;
import com.sjtusummerproject.ordermicroservice.Service.TicketService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService{
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Value("${ticketservice.url}")
    String baseUrl;

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

    @Override
    public List<TicketEntity> queryTicketByBatchIds(String ids) {
        String url=baseUrl+"/Ticket/QueryByBatchIds?"+"batchid="+ids;
        RestTemplate template = new RestTemplate();
        List<TicketEntity> result = template.getForObject(url,List.class);
        return result;
    }

    /*return "true" or "false"*/
    @Override
    public Boolean updateStockMinus(Long id, Long toMinus){
        String url = baseUrl+"/Ticket/MinusStock?"+"ticketid="+id+"&minus="+toMinus;
        RestTemplate template = new RestTemplate();
        Boolean res = template.getForObject(url,Boolean.class);
        return res;
    }

    /*return "true" or "false"*/
    @Override
    public Boolean updateStockPlus(Long id, Long toPlus){
        String url = baseUrl+"/Ticket/PlusStock?"+"ticketid="+id+"&minus="+toPlus;
        RestTemplate template = new RestTemplate();
        Boolean res = template.getForObject(url,Boolean.class);
        return res;
    }
}
