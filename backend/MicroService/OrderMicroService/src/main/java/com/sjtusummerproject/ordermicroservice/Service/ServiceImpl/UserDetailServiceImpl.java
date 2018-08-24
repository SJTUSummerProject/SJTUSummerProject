package com.sjtusummerproject.ordermicroservice.Service.ServiceImpl;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.TicketEntity;
import com.sjtusummerproject.ordermicroservice.DataModel.Domain.UserDetailEntity;
import com.sjtusummerproject.ordermicroservice.Service.UserDetailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
public class UserDetailServiceImpl implements UserDetailService {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Value("${userdetailservice.url}")
    String baseUrl;

    public UserDetailEntity queryUserDetailById(String token) {
        /* 发送给 UserDetailMicroService */
        String url=baseUrl+"/UserDetail/QueryByUserid?"+"token="+token;
        /* 注意：必须 http、https……开头，不然报错，浏览器地址栏不加 http 之类不出错是因为浏览器自动帮你补全了 */
        RestTemplate template = new RestTemplate();
        UserDetailEntity result = template.getForObject(url, UserDetailEntity.class);

        System.out.println("the result in query ticket "+result);
        return result;
    }

    public UserDetailEntity updateAccount(Long userid, double account){
        String url=baseUrl+"/UserDetail/UpdateByUserid?"+"userid="+userid+"&account="+account;
        RestTemplate template = new RestTemplate();
        UserDetailEntity result = template.getForObject(url, UserDetailEntity.class);
        return result;
    }

    @Override
    public Boolean updateAccountMinus(Long userid, double toMinus) {
        String url = baseUrl+"/UserDetail/MinusAccount?"+"userid="+userid+"&minus="+toMinus;
        RestTemplate template = new RestTemplate();
        Boolean res = template.getForObject(url,Boolean.class);
        return res;
    }

    @Override
    public Boolean updateAccountPlus(Long userid, double toPlus) {
        String url = baseUrl+"/UserDetail/PlusAccount?"+"userid="+userid+"&plus="+toPlus;
        RestTemplate template = new RestTemplate();
        Boolean res = template.getForObject(url,Boolean.class);
        return res;
    }
}
