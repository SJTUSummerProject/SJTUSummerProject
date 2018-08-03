package sjtusummerproject.signmicroservice.Service.ServiceImpl;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sjtusummerproject.signmicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.signmicroservice.SignmicroserviceApplicationTests;

import static org.junit.Assert.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedisUserManageServiceImplTest extends SignmicroserviceApplicationTests {

    @Test
    public void addUserStatusRedis() {
        String result = redisUserManageService.AddUserStatusRedis("pipipi");
        assertEquals("ok", result);
    }

    @Test
    public void queryUserStatusRedis() {
        String result = redisUserManageService.QueryUserStatusRedis("pipipi");
        assertEquals("ok", result);
    }

    @Test
    public void queryUserStatusRedisFail() {
        String result = redisUserManageService.QueryUserStatusRedis("ipipi");
        assertNull(result);
    }

    @Test
    public void addTokenUserRedisFirstLog() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        redisUserManageService.AddTokenUserRedis("1234", userEntity);
        assertEquals("1234", redisTemplate.opsForValue().get(1L));
    }
    @Test
    public void secondAddTokenUserRedisLog() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        redisUserManageService.AddTokenUserRedis("12345", userEntity);
        assertEquals("12345", redisTemplate.opsForValue().get(1L));
        assertNull(redisTemplate.opsForValue().get("1234"));
    }

    @Test
    public void zdeleteTokenRedis() {
        redisUserManageService.DeleteTokenRedis("12345");
        assertNull(redisTemplate.opsForValue().get("12345"));
        assertNull(redisTemplate.opsForValue().get(1l));
    }
}