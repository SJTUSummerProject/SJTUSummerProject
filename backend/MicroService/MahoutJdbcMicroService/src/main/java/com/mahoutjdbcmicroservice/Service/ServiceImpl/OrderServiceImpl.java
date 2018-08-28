package com.mahoutjdbcmicroservice.Service.ServiceImpl;

import com.mahoutjdbcmicroservice.Config.RestTemplateConfig;
import com.mahoutjdbcmicroservice.DataModel.Domain.OrderEntity;
import com.mahoutjdbcmicroservice.Service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Value("${orderservice.url}")
    String orderUrl;

    public List<OrderEntity> queryOrders(){
        String url = orderUrl+"/Order/QueryAll";
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new RestTemplateConfig());
        return template.getForObject(url,List.class);
    }
}
