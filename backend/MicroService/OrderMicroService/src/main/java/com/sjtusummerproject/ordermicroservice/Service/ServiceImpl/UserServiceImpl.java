package com.sjtusummerproject.ordermicroservice.Service.ServiceImpl;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.ordermicroservice.Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    String baseUrl="http://user-microservice:8080";

    @Override
    public UserEntity queryByUsername(String username) {
        /* 发送给 UserMicroService */
        String url=baseUrl+"/User/Query?"+"username="+username;
        /* 注意：必须 http、https……开头，不然报错，浏览器地址栏不加 http 之类不出错是因为浏览器自动帮你补全了 */
        RestTemplate template = new RestTemplate();
        UserEntity result = template.getForObject(url, UserEntity.class);

        System.out.println("the result in query user " + result);
        return result;
    }

    @Override
    public UserEntity queryById(Long userid) {
        /* 发送给 UserMicroService */
        String url=baseUrl+"/User//QueryById?"+"userid="+userid;
        /* 注意：必须 http、https……开头，不然报错，浏览器地址栏不加 http 之类不出错是因为浏览器自动帮你补全了 */
        RestTemplate template = new RestTemplate();
        UserEntity result = template.getForObject(url, UserEntity.class);
        return result;
    }
}
