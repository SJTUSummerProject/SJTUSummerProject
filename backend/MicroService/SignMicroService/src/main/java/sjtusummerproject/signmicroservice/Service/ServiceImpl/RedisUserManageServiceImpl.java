package sjtusummerproject.signmicroservice.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sjtusummerproject.signmicroservice.Service.RedisUserManageService;

import java.util.concurrent.TimeUnit;

@Service
public class RedisUserManageServiceImpl implements RedisUserManageService {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public String AddUserStatusRedis(String username) {
        System.out.println("in add redis the username "+username);
        redisTemplate.opsForValue().set(username,"ok",24,TimeUnit.HOURS);

        return "ok";
    }

    @Override
    public String QueryUserStatusRedis(String username) {
        System.out.println("in query redis the username "+username);
        String IsRedis = (String)redisTemplate.opsForValue().get(username);
        System.out.println("the isredis : "+IsRedis);
        return IsRedis;
    }

    @Override
    public String AddUserPasswordRedis(String username, String password) {
        System.out.println("in add user paddword redis");
        redisTemplate.opsForValue().set(username,password,24,TimeUnit.HOURS);
        return "ok";
    }

    @Override
    public String QueryUserPasswordRedis(String username) {
        System.out.println("in query user password redis");
        String password = (String )redisTemplate.opsForValue().get(username);
        return password;
    }
}
