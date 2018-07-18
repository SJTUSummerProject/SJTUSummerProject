package sjtusummerproject.usermicroservice.Service;

import sjtusummerproject.usermicroservice.DataModel.Domain.UserDetailEntity;

public interface ManageUserDetailService {
    public UserDetailEntity saveByUserId(Long userid,UserDetailEntity partUserDetail);
    public UserDetailEntity updateByUserId(Long userid,String avatar,String phone,String address,String account);
    public UserDetailEntity queryByUserId(Long userid);
}
