package com.sjtusummerproject.ordermicroservice.Service.ServiceImpl;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.CartEntity;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.TicketEntity;
import com.sjtusummerproject.ordermicroservice.Service.CartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    public RestTemplate restTemplate = new RestTemplate();

    @Value("${cartservice.url}")
    String baseUrl;

    @Override
    public CartEntity queryById(Long id) {
        /* 发送给 CartMicroService */
        String url=baseUrl+"/Cart/QueryById?"+"cartid="+id;
        /* 注意：必须 http、https……开头，不然报错，浏览器地址栏不加 http 之类不出错是因为浏览器自动帮你补全了 */
        RestTemplate template = new RestTemplate();
        CartEntity result = template.getForObject(url, CartEntity.class);

        System.out.println("the result in query CartEntity "+result);
        return result;
    }

    @Override
    public List<CartEntity> queryByBatchIds(String cartids) {
        /* 假设cartids的形式是这样的 : [1, 2, 3, 4]*/
        String url = baseUrl+"/Cart/QueryBatchByIds";
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("batchid", cartids);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(multiValueMap, headers);
        ParameterizedTypeReference<List<CartEntity>> typeRef = new ParameterizedTypeReference<List<CartEntity>>() {
        };
        ResponseEntity<List<CartEntity>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, typeRef);
        return responseEntity.getBody();
    }
}
