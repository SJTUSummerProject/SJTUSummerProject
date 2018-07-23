package com.sjtusummerproject.ordermicroservice.Service.ServiceImpl;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.ordermicroservice.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    RestTemplate restTemplate;
    @Value("${authservice.url}")
    private String authUrl;

    @Override
    public UserEntity callAuthService(String token) {
        System.out.println("the token is : " +token);
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
        multiValueMap.add("token", token);
        return restTemplate.postForObject(authUrl, multiValueMap, UserEntity.class);
    }

    public int authUser(UserEntity userEntity){
        if (userEntity == null) return 1;
        else if (!userEntity.getAuthority().equals("Customer")) return 2;
        else if (userEntity.getStatus().equals("Frozen")) return 3;
        else return 0;
    }
}
