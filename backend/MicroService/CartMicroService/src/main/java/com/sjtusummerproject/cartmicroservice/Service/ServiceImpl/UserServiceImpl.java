package com.sjtusummerproject.cartmicroservice.Service.ServiceImpl;

import com.sjtusummerproject.cartmicroservice.DataModel.Domain.TicketEntity;
import com.sjtusummerproject.cartmicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.cartmicroservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService{
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${userservice.url}")
    String baseUrl="http://user-microservice:8080";

    @Override
    public UserEntity queryByUsername(String username) {
        /* 发送给 UserMicroService */
        String url=baseUrl+"/User/Query?"+"username="+username;
        /* 注意：必须 http、https……开头，不然报错，浏览器地址栏不加 http 之类不出错是因为浏览器自动帮你补全了 */
        UserEntity result = restTemplate.getForObject(url, UserEntity.class);

        //System.out.println("the result in query user " + result);
        return result;
    }

    @Override
    public UserEntity queryById(Long userid) {
        /* 发送给 UserMicroService */
        String url=baseUrl+"/User//QueryById?"+"userid="+userid;
        /* 注意：必须 http、https……开头，不然报错，浏览器地址栏不加 http 之类不出错是因为浏览器自动帮你补全了 */
        UserEntity result = restTemplate.getForObject(url, UserEntity.class);
        return result;
    }

    @Override
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
