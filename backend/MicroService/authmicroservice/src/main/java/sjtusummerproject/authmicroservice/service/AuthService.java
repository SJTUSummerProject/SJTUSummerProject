package sjtusummerproject.authmicroservice.service;

import sjtusummerproject.authmicroservice.entity.UserEntity;

public interface AuthService {
    public UserEntity AuthUser(String token);
}
