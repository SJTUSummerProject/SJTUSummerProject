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

    @Override
    public UserEntity QueryUserByIdOption(Long id) {
        return userRepository.findById(id);
    }

    /* 此处有硬编码！-将authority设置为customer */
    @Override
    public UserEntity AddUserOption(String username, String password, String email, String status) {
        UserEntity userToAdd = new UserEntity();
        userToAdd.setId(0L);
        userToAdd.setUsername(username);
        userToAdd.setPassword(password);
        userToAdd.setEmail(email);
        userToAdd.setStatus(status);
        userToAdd.setAuthority("Customer");/* 硬编码！ */
        /* username is unique */
        try {
            return userRepository.save(userToAdd);
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public void DeleteUserOption(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public void UpdateUserStatusOption(String username, String status) {
        UserEntity userToUpdateStatus = userRepository.findByUsername(username.trim());
        //System.out.println("the username id 3 "+username.trim());
        //System.out.println("the username id 1 "+userToUpdateStatus.getId());
        userToUpdateStatus.setStatus(status);
        //System.out.println("the username id 2 "+userToUpdateStatus.getId());
        userRepository.save(userToUpdateStatus);
    }

    @Override
    public boolean UpdateUserOldPassword(Long id, String oldPassword, String newPassword){
        UserEntity userEntity = userRepository.findById(id);
        if (userEntity == null) return false;
        else if (!userEntity.getPassword().equals(oldPassword)) return false;
        else {
            userEntity.setPassword(newPassword);
            userRepository.save(userEntity);
            return true;
        }
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
