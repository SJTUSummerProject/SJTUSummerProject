package sjtusummerproject.codemicroservice.Service.ServiceImpl;

import org.junit.Test;
import sjtusummerproject.codemicroservice.CodemicroserviceApplicationTests;

import static org.junit.Assert.*;

public class RedisAnswerUuidManageServiceImplTest extends CodemicroserviceApplicationTests {

    @Test
    public void testRedisTemplate(){
        redisAnswerUuidManageService.AddAnswerUuidRedis("1234","1234");
        String answer = redisAnswerUuidManageService.QueryAnswerRedis("1234");
        assertEquals("1234", answer);
    }

    @Test
    public void testNull(){
        String answer = redisAnswerUuidManageService.QueryAnswerRedis("11");
        assertNull(answer);
    }
}