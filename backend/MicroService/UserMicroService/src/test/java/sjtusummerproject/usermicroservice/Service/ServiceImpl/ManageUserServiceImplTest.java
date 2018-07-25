package sjtusummerproject.usermicroservice.Service.ServiceImpl;

import org.apache.catalina.User;
import org.junit.*;
import org.junit.runners.MethodSorters;
import sjtusummerproject.usermicroservice.DataModel.Dao.UserRepository;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.usermicroservice.Service.ManageUserService;
import sjtusummerproject.usermicroservice.UsermicroserviceApplicationTests;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ManageUserServiceImplTest extends UsermicroserviceApplicationTests {
    @Test
    public void addUserOption() {
        UserEntity user = manageUserService.AddUserOption("pzy","123456","123456@qq.com","UnActive");
        assertEquals("something wrong from the addUserOption","pzy",user.getUsername());
    }

    @Test
    public void queryUserOptionSuccess() {
        UserEntity user = manageUserService.QueryUserOption("pzy");
        assertEquals("pzy", user.getUsername());
    }

    @Test
    public void queryUserOptionFailure() {
        UserEntity user = manageUserService.QueryUserOption("pzyyyy");
        assertNull(user);
    }
    @Test
    public void queryUserByIdOption() {
        UserEntity user = manageUserService.QueryUserByIdOption(1L);
        UserEntity userEntity = generateUserEntity();
        assertEquals(user.getUsername(), userEntity.getUsername());
    }

    @Test
    public void updateUserStatusOptionSuccess() {
        UserEntity userEntity = manageUserService.UpdateUserStatusOption("pzy", "Active");
        assertEquals( "Active",userEntity.getStatus());
    }

    @Test
    public void updateUserStatusOptionFail() {
        UserEntity userEntity = manageUserService.UpdateUserStatusOption("ppppp", "Active");
        assertNull(userEntity);
    }

    @Test
    public void updateUserOldPasswordFail() {
        boolean result = manageUserService.UpdateUserOldPassword(1L, "11111", "12345678");
        UserEntity userEntity = manageUserService.QueryUserByIdOption(1L);
        assertEquals(false, result);
        assertEquals("123456", userEntity.getPassword());
    }

    @Test
    public void updateUserOldPasswordSuccess() {
        boolean result = manageUserService.UpdateUserOldPassword(1L, "123456", "12345678");
        UserEntity userEntity = manageUserService.QueryUserByIdOption(1L);
        assertEquals(true, result);
        assertEquals("12345678", userEntity.getPassword());
    }

    @Test
    public void xupdateUserPasswordOption() {
        UserEntity userEntity = manageUserService.UpdateUserPasswordOption("pzy", "1111");
        assertEquals("1111", userEntity.getPassword());
    }

    @Test
    public void updateUserPasswordOptionFail() {
        UserEntity userEntity = manageUserService.UpdateUserPasswordOption("pzyyyy", "1111");
        assertNull(userEntity);
    }

    @Test
    public void updateUserAuthorityOption() {
        UserEntity userEntity = manageUserService.UpdateUserAuthorityOption("pzy", "manager");
        assertEquals("manager", userEntity.getAuthority());
    }

    @Test
    public void updateUserAuthorityOptionFail() {
        UserEntity userEntity = manageUserService.UpdateUserAuthorityOption("pzyyyy", "manager");
        assertNull(userEntity);
    }

    @Test
    public void zdeleteOption(){
        manageUserService.DeleteUserOption(1l);
        UserEntity userEntity = userRepository.findById(1l);
        assertNull(userEntity);
    }

}