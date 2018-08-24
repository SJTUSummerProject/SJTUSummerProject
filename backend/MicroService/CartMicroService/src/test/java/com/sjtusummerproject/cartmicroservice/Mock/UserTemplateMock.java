package com.sjtusummerproject.cartmicroservice.Mock;

import com.sjtusummerproject.cartmicroservice.DataModel.Domain.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class UserTemplateMock extends RestTemplate {
    @Override
    public <T> T getForObject(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1l);
        return (T) userEntity;
    }
}
