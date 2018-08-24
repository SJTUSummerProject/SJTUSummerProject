package com.sjtusummerproject.commentmicroservice.Service;

import com.sjtusummerproject.commentmicroservice.DataModel.Domain.UserEntity;

public interface AuthService {
    public UserEntity callAuthService(String token);
    public int authUser(UserEntity userEntity);
}
