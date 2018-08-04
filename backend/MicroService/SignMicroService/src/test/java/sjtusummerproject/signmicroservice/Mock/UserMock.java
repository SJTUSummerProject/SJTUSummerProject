package sjtusummerproject.signmicroservice.Mock;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import sjtusummerproject.signmicroservice.DataModel.Domain.UserEntity;

import java.net.URI;
import java.util.Map;

@Component
public class UserMock extends RestTemplate {
    @Override
    public <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return null;
    }

    @Override
    public <T> T getForObject(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("pzy");
        userEntity.setPassword("123456");
        return (T)userEntity;
    }
}
