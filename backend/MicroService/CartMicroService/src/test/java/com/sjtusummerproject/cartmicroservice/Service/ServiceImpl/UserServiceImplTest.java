package com.sjtusummerproject.cartmicroservice.Service.ServiceImpl;

import com.sjtusummerproject.cartmicroservice.CartmicroserviceApplicationTests;
import com.sjtusummerproject.cartmicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.cartmicroservice.Mock.UserTemplateMock;
import com.sjtusummerproject.cartmicroservice.Service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserServiceImplTest extends CartmicroserviceApplicationTests {

    @Autowired
    UserService userService;
    @Autowired
    UserTemplateMock userTemplateMock;

    @Test
    public void queryByUsername() {
        userService.setRestTemplate(userTemplateMock);
        UserEntity userEntity = userService.queryByUsername("pzy");
        assertEquals(1l, (long) userEntity.getId());
    }

    @Test
    public void queryById() {
        userService.setRestTemplate(userTemplateMock);
        UserEntity userEntity = userService.queryById(1l);
        assertEquals(1l, (long) userEntity.getId());
    }

}