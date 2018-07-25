package sjtusummerproject.authmicroservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import sjtusummerproject.authmicroservice.service.AuthService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = {"classpath:application-test.properties"})
public class AuthmicroserviceApplicationTests {
    @Autowired
    public RedisTemplate redisTemplate;
    @Autowired
    public AuthService authService;

    @Test
    public void contextLoads() {
    }

}
