package sjtusummerproject.authmicroservice.service.serviceImpl;

import net.sf.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;
import sjtusummerproject.authmicroservice.AuthmicroserviceApplicationTests;
import sjtusummerproject.authmicroservice.entity.UserEntity;

import static org.junit.Assert.*;

public class AuthServiceImplTest extends AuthmicroserviceApplicationTests {
    @Before
    public void setup(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        JSONObject jsonObject = JSONObject.fromObject(userEntity);
        String string = jsonObject.toString();
        redisTemplate.opsForValue().set("1234", string);
    }
    @Test
    public void authUser() {
        UserEntity userEntity = authService.AuthUser("1234");
        assertEquals(1L,(long)userEntity.getId());
    }

    @Test
    public void authUserFail(){
        UserEntity userEntity = authService.AuthUser("111");
        assertNull(userEntity);
    }
    @After
    public void delete(){
        redisTemplate.delete("1234");
    }
}