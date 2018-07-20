package com.sjtusummerproject.commentmicroservice.Service;

import com.sjtusummerproject.commentmicroservice.DataModel.Domain.UserEntity;

public interface UserService {
    public UserEntity queryById(Long userid);
}
