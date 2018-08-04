package sjtusummerproject.usermicroservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import sjtusummerproject.usermicroservice.DataModel.Dao.UserRepository;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.usermicroservice.Service.ManageUserService;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = {"classpath:application-test.properties"})
@SpringBootTest
public class UsermicroserviceApplicationTests {
	@Autowired
	public ManageUserService manageUserService;
	@Autowired
	public UserRepository userRepository;

	public UserEntity generateUserEntity() {
		UserEntity userEntity = new UserEntity();
		userEntity.setPassword("123456");
		userEntity.setEmail("123456@qq.com");
		userEntity.setId(1l);
		userEntity.setAuthority("Customer");
		userEntity.setStatus("UnActive");
		userEntity.setUsername("pzy");
		return userEntity;
	}
	@Test
	public void contextLoads() {
	}

}
