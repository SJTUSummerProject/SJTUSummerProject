package sjtusummerproject.signmicroservice.Mock;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@Component
public class CodeSuccessMock extends RestTemplate {
    @Override
    public <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        Boolean result = true;
        return (T)result;
    }
}
