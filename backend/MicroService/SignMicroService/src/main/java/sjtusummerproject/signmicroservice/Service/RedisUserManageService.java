package sjtusummerproject.signmicroservice.Service;

import sjtusummerproject.signmicroservice.DataModel.Domain.UserEntity;

public interface RedisUserManageService {
    public String QueryUserStatusRedis(String username);
    public String AddUserStatusRedis(String username);
    public void AddTokenUserRedis(String token, UserEntity user);
    public void DeleteTokenRedis(String token);
}
