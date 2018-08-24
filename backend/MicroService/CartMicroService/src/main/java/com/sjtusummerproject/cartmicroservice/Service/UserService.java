package com.sjtusummerproject.cartmicroservice.Service;

import com.sjtusummerproject.cartmicroservice.DataModel.Domain.UserEntity;
import org.springframework.web.client.RestTemplate;

public interface UserService {
    public UserEntity queryByUsername(String username);
    public UserEntity queryById(Long userid);
    void setRestTemplate(RestTemplate restTemplate);
}
