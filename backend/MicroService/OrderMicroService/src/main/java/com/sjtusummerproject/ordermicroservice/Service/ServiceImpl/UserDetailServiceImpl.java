package com.sjtusummerproject.ordermicroservice.Service.ServiceImpl;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.TicketEntity;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.UserDetailEntity;
import com.sjtusummerproject.ordermicroservice.Service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserDetailServiceImpl implements UserDetailService {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    String baseUrl="http://user-microservice:8080";

    public UserDetailEntity queryUserDetailById(Long userid) {
        /* 发送给 UserDetailMicroService */
        String url=baseUrl+"/UserDetail/QueryByUserid?"+"userid="+userid;
        /* 注意：必须 http、https……开头，不然报错，浏览器地址栏不加 http 之类不出错是因为浏览器自动帮你补全了 */
        RestTemplate template = new RestTemplate();
        UserDetailEntity result = template.getForObject(url, UserDetailEntity.class);

        System.out.println("the result in query ticket "+result);
        return result;
    }

    public UserDetailEntity updateAccount(Long userid, double account){
        String url=baseUrl+"/UserDetail/UpdateByUserid?"+"userid="+userid+"&account="+account;
        RestTemplate template = new RestTemplate();
        UserDetailEntity result = template.getForObject(url, UserDetailEntity.class);
        return result;
    }
}
