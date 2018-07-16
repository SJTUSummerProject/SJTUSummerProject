package sjtusummerproject.signmicroservice.Service.ServiceImpl;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sjtusummerproject.signmicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.signmicroservice.Service.RedisUserManageService;

import java.util.concurrent.TimeUnit;

@Service
public class RedisUserManageServiceImpl implements RedisUserManageService {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public String AddUserStatusRedis(String username) {
        //System.out.println("in add redis the username "+username);
        redisTemplate.opsForValue().set(username,"ok",24,TimeUnit.HOURS);

        return "ok";
    }

    @Override
    public String QueryUserStatusRedis(String username) {
        //System.out.println("in query redis the username "+username);
        String IsRedis = (String)redisTemplate.opsForValue().get(username);
        //System.out.println("the isredis : "+IsRedis);
        return IsRedis;
    }

    @Override
    public void AddTokenUserRedis(String token, UserEntity user){
        JSONObject jsonObject = JSONObject.fromObject(user);
        String userString = jsonObject.toString();
        System.out.println("userJson: "+userString);
        redisTemplate.opsForValue().set(token, userString, 24, TimeUnit.HOURS);
    }


    @Override
    public void DeleteTokenRedis(String token){
        redisTemplate.delete(token);
    }
}
