package sjtusummerproject.signmicroservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import sjtusummerproject.signmicroservice.Mock.CodeFailMock;
import sjtusummerproject.signmicroservice.Mock.CodeSuccessMock;
import sjtusummerproject.signmicroservice.Mock.UserMock;
import sjtusummerproject.signmicroservice.Service.ServiceImpl.InvokeCodeServiceImpl;
import sjtusummerproject.signmicroservice.Service.ServiceImpl.InvokeEmailMessageServiceImpl;
import sjtusummerproject.signmicroservice.Service.ServiceImpl.InvokeUserServiceImpl;
import sjtusummerproject.signmicroservice.Service.ServiceImpl.RedisUserManageServiceImpl;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = {"classpath:application-test.properties"})
@SpringBootTest
public class SignmicroserviceApplicationTests {

    @Autowired
	public InvokeCodeServiceImpl invokeCodeService;
    @Autowired
	public CodeFailMock codeFailMock;
    @Autowired
	public UserMock userMock;
    @Autowired
	public InvokeUserServiceImpl invokeUserService;
    @Autowired
	public CodeSuccessMock codeSuccessMock;
	@Autowired
	public RedisTemplate redisTemplate;
	@Autowired
	public RedisUserManageServiceImpl redisUserManageService;
	@Test
	public void contextLoads() {
	}

}
