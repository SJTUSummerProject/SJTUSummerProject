package sjtusummerproject.emailmicroservice.Service;

import sjtusummerproject.emailmicroservice.DataModel.Domain.UserUuidEntity;

public interface UserUuidManageService {
    public String AddUserUuidService(UserUuidEntity userUuidEntity);
    public UserUuidEntity QueryUserUuidService(String uuid);
}
