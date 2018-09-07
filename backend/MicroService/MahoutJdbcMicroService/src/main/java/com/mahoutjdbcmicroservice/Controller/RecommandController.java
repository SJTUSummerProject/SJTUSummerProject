package com.mahoutjdbcmicroservice.Controller;

import com.mahoutjdbcmicroservice.DataModel.Domain.UserEntity;
import com.mahoutjdbcmicroservice.Service.UserRecommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value = "/Recommand")
public class RecommandController {

    @Autowired
    UserRecommandService userRecommandService;

    @Value("${authservice.url}")
    String url;

    RestTemplate restTemplate = new RestTemplate();

    //一次最大返回六个
    @RequestMapping(value = "/QueryRecommandTicket")
    @ResponseBody
    public List<Long> queryRecommandTicket(@RequestParam(name = "token") String token){
        List<Long> result = new LinkedList<>();
        UserEntity userEntity = callAuthService(token);
        int errorNum = authUser(userEntity);
        if (errorNum == 0) result = userRecommandService.queryTicketByUserid(userEntity.getId());
        return result;
    }

    private UserEntity callAuthService(String token){
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("token", token);
        return restTemplate.postForObject(url, multiValueMap, UserEntity.class);
    }

    /*此时这个user的身份一定要是manager*/
    private int authUser(UserEntity userEntity){
        if (userEntity == null) return 1;
        else if (userEntity.getAuthority().equals("Manager")) return 2;
        else if (userEntity.getStatus().equals("Frozen")) return 3;
        else return 0;
    }
}
