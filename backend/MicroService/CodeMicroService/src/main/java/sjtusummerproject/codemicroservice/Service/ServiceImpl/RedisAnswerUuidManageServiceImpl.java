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
    public String QueryAnswerRedis(String answer) {
        String Answer = (String)redisTemplate.opsForValue().get(answer);
        return Answer;
    }

    @Override
    public void AddAnswerUuidRedis(String answer, String flag) {
        redisTemplate.opsForValue().set(answer, flag,10l, TimeUnit.MINUTES);
    }
}
