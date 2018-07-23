package com.sjtusummerproject.ordermicroservice.Service;


import com.sjtusummerproject.ordermicroservice.DataModel.Domain.UserEntity;

public interface AuthService {
    public UserEntity callAuthService(String token);
    public int authUser(UserEntity userEntity);
}
