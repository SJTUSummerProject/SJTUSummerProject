package sjtusummerproject.signmicroservice.Service;

import sjtusummerproject.signmicroservice.DataModel.Domain.UserEntity;

import javax.swing.text.html.parser.Entity;

public interface InvokeUserService {
    public String AddUserMicroService(UserEntity user);
    public UserEntity QueryUserMicroService(String userName);
    public UserEntity validUser(String name, String password);
    public UserEntity GenerateUser(String username, String passsword, String Email, String status);
}
