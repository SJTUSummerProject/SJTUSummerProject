package sjtusummerproject.usermicroservice.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return userRepository.save(userToAdd);
    }

    @Override
    public void DeleteUserOption(Long id) {
        if(userRepository.findById(id) == null)
            return;
        userRepository.delete(id);
    }

    @Override
    public UserEntity UpdateUserStatusOption(String username, String status) {
        UserEntity userToUpdateStatus = userRepository.findByUsername(username.trim());
        if (userToUpdateStatus == null) return null;
        userToUpdateStatus.setStatus(status);
        return userRepository.save(userToUpdateStatus);
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
    public UserEntity UpdateUserPasswordOption(String username, String password) {
        UserEntity userToUpdatePassword = userRepository.findFirstByUsername(username);
        if (userToUpdatePassword == null) return null;
        userToUpdatePassword.setPassword(password);
        return userRepository.save(userToUpdatePassword);
    }

    @Override
    public UserEntity UpdateUserAuthorityOption(String username, String authority) {
        UserEntity userToUpdateAuthority = userRepository.findFirstByUsername(username);
        if (userToUpdateAuthority == null) return null;
        userToUpdateAuthority.setAuthority(authority);
        return userRepository.save(userToUpdateAuthority);
    }

    /* for manager */
    @Override
    public UserEntity UpdateUserStatusOptionById(Long id, String status) {
        UserEntity userToUpdateStatus = userRepository.findById(id);
        if (userToUpdateStatus == null) return null;
        userToUpdateStatus.setStatus(status);
        return userRepository.save(userToUpdateStatus);
    }

    @Override
    public UserEntity UpdateUserPasswordOptionById(Long id, String password) {
        UserEntity userToUpdatePassword = userRepository.findById(id);
        if (userToUpdatePassword == null) return null;
        userToUpdatePassword.setPassword(password);
        return userRepository.save(userToUpdatePassword);
    }

    @Override
    public UserEntity UpdateUserAuthorityOptionById(Long id, String authority) {
        UserEntity userToUpdateAuthority = userRepository.findById(id);
        if (userToUpdateAuthority == null) return null;
        userToUpdateAuthority.setAuthority(authority);
        return userRepository.save(userToUpdateAuthority);
    }

    @Override
    public Page<UserEntity> QueryBatch(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
