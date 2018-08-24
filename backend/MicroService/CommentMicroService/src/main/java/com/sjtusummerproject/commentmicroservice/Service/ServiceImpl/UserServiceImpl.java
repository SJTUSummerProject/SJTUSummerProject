package com.sjtusummerproject.commentmicroservice.Service.ServiceImpl;

import com.sjtusummerproject.commentmicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.commentmicroservice.Service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Value("${userservice.url}")
    String baseUrl;

    @Override
    public UserEntity queryById(Long userid) {
        /* 发送给 UserMicroService */
        String url=baseUrl+"/User/QueryById?"+"userid="+userid;
        System.out.println("the url "+url);
        /* 注意：必须 http、https……开头，不然报错，浏览器地址栏不加 http 之类不出错是因为浏览器自动帮你补全了 */
        RestTemplate template = new RestTemplate();
        UserEntity result = template.getForObject(url, UserEntity.class);
        return result;
    }
}