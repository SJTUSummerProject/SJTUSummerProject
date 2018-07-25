package sjtusummerproject.userdetailmicroservice.Service.ServiceImp;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import sjtusummerproject.userdetailmicroservice.DataModel.Dao.UserDetailRepository;
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
    }

    @Test
    public void updateByUserId() {
    }

    @Test
    public void queryByUserId() {
    }

    @Test
    public void updateAccountMinusById() {
    }

    @Test
    public void updateAccountPlusById() {
    }

    @Test
    public void saveAvatar() {
    }
}