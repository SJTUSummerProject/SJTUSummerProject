package sjtusummerproject.usermicroservice.Service;

import sjtusummerproject.usermicroservice.DataModel.Domain.UserDetailEntity;

public interface ManageUserDetailService {
    public UserDetailEntity saveByUserId(Long userid,UserDetailEntity partUserDetail);
}
