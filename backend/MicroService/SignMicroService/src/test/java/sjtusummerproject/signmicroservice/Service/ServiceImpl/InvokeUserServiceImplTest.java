package sjtusummerproject.signmicroservice.Service.ServiceImpl;

import org.junit.Test;
import sjtusummerproject.signmicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.signmicroservice.Mock.UserMock;
import sjtusummerproject.signmicroservice.Service.InvokeUserService;
import sjtusummerproject.signmicroservice.SignmicroserviceApplicationTests;

import static org.junit.Assert.*;

public class InvokeUserServiceImplTest extends SignmicroserviceApplicationTests {

    @Test
    public void addUserMicroService() {
        invokeUserService.setRestTemplate(new UserMock());
        String result = invokeUserService.AddUserMicroService(new UserEntity());
        assertEquals("success", result);
    }

    @Test
    public void addUserMicroServiceFail() {
        invokeUserService.setRestTemplate(userMock);
        String result = invokeUserService.AddUserMicroService(null);
        assertEquals("fail", result);
    }

    @Test
    public void queryUserMicroService() {
        invokeUserService.setRestTemplate(userMock);
        UserEntity userEntity = invokeUserService.QueryUserMicroService("123");
        assertEquals(1l ,(long)userEntity.getId());
    }

    @Test
    public void validUser() {
        invokeUserService.setRestTemplate(userMock);
        UserEntity userEntity = invokeUserService.validUser("pzy", "123456");
        assertEquals(1l, (long)userEntity.getId());
        userEntity = invokeUserService.validUser("pzy", "2222");
        assertNull(userEntity);
    }

    @Test
    public void generateUser() {
        UserEntity userEntity = invokeUserService.GenerateUser("123","1","1","1");
        assertEquals("UnActive", userEntity.getStatus());
    }

}