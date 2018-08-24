package sjtusummerproject.usermicroservice.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserEntity;

public interface ManageUserService {
    public UserEntity QueryUserOption(String username);
    public UserEntity QueryUserByIdOption(Long id);
    public UserEntity AddUserOption(String username, String password, String email, String status);
    public boolean UpdateUserOldPassword(Long id, String oldPassword, String newPassword);
    public void DeleteUserOption(Long id);
    public UserEntity UpdateUserStatusOption(String username, String status);
    public UserEntity UpdateUserPasswordOption(String username, String password);
    public UserEntity UpdateUserAuthorityOption(String username,String authority);

    public UserEntity UpdateUserStatusOptionById(Long id, String status);
    public UserEntity UpdateUserPasswordOptionById(Long id, String password);
    public UserEntity UpdateUserAuthorityOptionById(Long id, String authority);
    public Page<UserEntity> QueryBatch(Pageable pageable);
}