package sjtusummerproject.usermicroservice.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sjtusummerproject.usermicroservice.DataModel.Dao.UserRepository;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.usermicroservice.Service.ManageUserService;

@Service
public class ManageUserServiceImpl implements ManageUserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserEntity QueryUserOption(String username) {
        return userRepository.findFirstByUsername(username);
    }

    /* 此处有硬编码！-将authority设置为customer */
    @Override
    public String AddUserOption(String username, String password, String email, String status) {
        System.out.println("in add user");
        UserEntity userToAdd = new UserEntity();
        userToAdd.setId(0L);
        userToAdd.setUsername(username);
        userToAdd.setPassword(password);
        userToAdd.setEmail(email);
        userToAdd.setStatus(status);
        userToAdd.setAuthority("Customer");/* 硬编码！ */
        /* username is unique */
        try {
            userRepository.save(userToAdd);
            return "ok";
        }
        catch (Exception e){
            return "error";
        }
    }

    @Override
    public void DeleteUserOption(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public void UpdateUserStatusOption(String username, String status) {
        UserEntity userToUpdateStatus = userRepository.findFirstByUsername(username);
        userToUpdateStatus.setStatus(status);
        userRepository.save(userToUpdateStatus);
    }

    @Override
    public void UpdateUserPasswordOption(String username, String password) {
        UserEntity userToUpdatePassword = userRepository.findFirstByUsername(username);
        userToUpdatePassword.setPassword(password);
        userRepository.save(userToUpdatePassword);
    }

    @Override
    public void UpdateUserEmailOption(String username, String email) {
        UserEntity userToUpdateEmail = userRepository.findFirstByUsername(username);
        userToUpdateEmail.setPassword(email);
        userRepository.save(userToUpdateEmail);
    }

    @Override
    public void UpdateUserAuthorityOption(String username, String authority) {
        UserEntity userToUpdateAuthority = userRepository.findFirstByUsername(username);
        userToUpdateAuthority.setPassword(authority);
        userRepository.save(userToUpdateAuthority);
    }
}
