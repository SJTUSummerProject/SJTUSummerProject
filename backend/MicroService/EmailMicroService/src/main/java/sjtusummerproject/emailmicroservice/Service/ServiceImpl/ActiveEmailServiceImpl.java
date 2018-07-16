package sjtusummerproject.emailmicroservice.Service.ServiceImpl;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sjtusummerproject.emailmicroservice.Service.ActiveEmailService;

import java.lang.annotation.Target;

@Service
public class ActiveEmailServiceImpl implements ActiveEmailService {

    @Autowired
    @Qualifier("redisTemplate")
    RedisTemplate redistTemplate;
    /**
     * 激活用户
     * @param code 用户激活码
     * @return 是否激活成功
     */
    public Boolean Active(String code){
        System.out.println("Active!");
        System.out.println("uuid "+code);

        String username = (String)redistTemplate.opsForValue().get(code);
        System.out.println("the uuid username : "+ username);


        // 1、使用postForObject请求接口
        if(username!=null && username!=""){
            //如果存在用户，将此用户状态设为可用
            /* 发送给 UserMicroService */
            String url="http://user-microservice:8080/User/UpdateStatus";
            /* 注意：必须 http、https……开头，不然报错，浏览器地址栏不加 http 之类不出错是因为浏览器自动帮你补全了 */
            System.out.println("即将发请求 update status");
            RestTemplate template = new RestTemplate();
            // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
            MultiValueMap<String,String> postbody = new LinkedMultiValueMap<>();
            postbody.add("username",username);
            postbody.add("status","Active");

            // 1、使用postForObject请求接口
            String result = template.postForObject(url, postbody, String.class);
            return true;
        }else{
            return false;
        }
    }
}