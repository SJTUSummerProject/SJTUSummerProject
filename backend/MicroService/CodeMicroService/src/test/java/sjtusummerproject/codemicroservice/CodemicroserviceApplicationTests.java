package sjtusummerproject.codemicroservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import sjtusummerproject.codemicroservice.Mock.MockRandom;
import sjtusummerproject.codemicroservice.Service.ServiceImpl.GenerateCodeServiceImpl;
import sjtusummerproject.codemicroservice.Service.ServiceImpl.RedisAnswerUuidManageServiceImpl;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = {"classpath:application-test.properties"})
@SpringBootTest
public class CodemicroserviceApplicationTests {
	@Autowired
    public GenerateCodeServiceImpl generateCodeService;
	@Autowired
	public MockRandom mockRandom;
	@Autowired
	public RedisTemplate redisTemplate;
	@Autowired
	public RedisAnswerUuidManageServiceImpl redisAnswerUuidManageService;
	@Test
	public void contextLoads() {
	}

}
