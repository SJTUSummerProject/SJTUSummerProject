package sjtusummerproject.codemicroservice.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sjtusummerproject.codemicroservice.Service.RedisAnswerUuidManageService;

import java.util.concurrent.TimeUnit;

@Service
public class RedisAnswerUuidManageServiceImpl implements RedisAnswerUuidManageService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String QueryAnswerRedis(String Uuid) {
        System.out.println("in query answer uuid redis");
        String Answer = (String)redisTemplate.opsForValue().get(Uuid);
        return Answer;
    }

    @Override
    public String AddAnswerUuidRedis(String Uuid, String Answer) {
        System.out.println("in query answer redis");
        redisTemplate.opsForValue().set(Uuid,Answer,24, TimeUnit.HOURS);
        return "ok";
    }
}
