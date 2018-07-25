package sjtusummerproject.userdetailmicroservice.Service.ServiceImp;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import sjtusummerproject.userdetailmicroservice.DataModel.Dao.UserDetailRepository;
import sjtusummerproject.userdetailmicroservice.DataModel.Domain.UserDetailEntity;
import sjtusummerproject.userdetailmicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.userdetailmicroservice.UserdetailmicroserviceApplicationTests;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ManageUserDetailServiceImplTest extends UserdetailmicroserviceApplicationTests {

    @Autowired
    UserDetailRepository userDetailRepository;
    @Autowired
    ManageUserDetailServiceImpl manageUserDetailService;

    @Test
    public void asaveByUserId() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1l);
        userEntity.setUsername("pipipan");
        manageUserDetailService.saveByUserId(userEntity);
        UserDetailEntity userDetailEntity = userDetailRepository.findById(1l);
        assertEquals("pipipan", userDetailEntity.getUsername());
        assertNull(userDetailEntity.getPhone());
    }

    @Test
    public void bupdateByUserId() {
        UserDetailEntity userDetailEntity = manageUserDetailService.updateByUserId(1l,null,"123","123",123.0,"123");
        assertEquals("123", userDetailEntity.getPhone());
    }

    @Test
    public void bupdateByUserIdfail() {
        UserDetailEntity userDetailEntity = manageUserDetailService.updateByUserId(12l,null,"123","123",123.0,"123");
        assertNull(userDetailEntity);
    }


    @Test
    public void queryByUserId() {
        UserDetailEntity userDetailEntity = manageUserDetailService.queryByUserId(1l);
        assertEquals("123", userDetailEntity.getPhone());
    }

    @Test
    public void queryByUserIdfail() {
        UserDetailEntity userDetailEntity = manageUserDetailService.queryByUserId(5l);
        assertNull(userDetailEntity);
    }
    @Test
    public void cupdateAccountMinusById() {
        manageUserDetailService.updateAccountPlusById(1l,1.0);
        UserDetailEntity userDetailEntity = userDetailRepository.findById(1l);
        assertEquals(124.0, userDetailEntity.getAccount(),0.2);
    }

    @Test
    public void updateAccountPlusById() {
        manageUserDetailService.updateAccountMinusById(1l,1.0);
        UserDetailEntity userDetailEntity = userDetailRepository.findById(1l);
        assertEquals(123.0, userDetailEntity.getAccount(),0.2);
    }

}