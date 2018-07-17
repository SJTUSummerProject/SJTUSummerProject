package com.sjtusummerproject.ordermicroservice.Service;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.UserEntity;

public interface UserService {
    public UserEntity queryByUsername(String username);
    public UserEntity queryById(Long userid);
}
