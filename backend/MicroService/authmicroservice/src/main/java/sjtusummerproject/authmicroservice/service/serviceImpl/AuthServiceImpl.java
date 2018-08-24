package sjtusummerproject.authmicroservice.service.serviceImpl;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sjtusummerproject.authmicroservice.entity.UserEntity;
import sjtusummerproject.authmicroservice.service.AuthService;


@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public UserEntity AuthUser(String token) {
        String userString = (String)redisTemplate.opsForValue().get(token);
        if (userString == null) return null;
        else {
            JSONObject jsonObject = JSONObject.fromObject(userString);
            UserEntity result = (UserEntity)JSONObject.toBean(jsonObject, UserEntity.class);
            //System.out.println(result.getUsername());
            return result;
        }
    }
}
