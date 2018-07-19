package sjtusummerproject.usermicroservice.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserDetailEntity;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.usermicroservice.Service.ManageUserDetailService;
import sjtusummerproject.usermicroservice.Service.ManageUserService;

@Service
public class ManageUserDetailServiceImpl implements ManageUserDetailService {

    @Autowired
    ManageUserService manageUserService;

    @Override
    public UserDetailEntity saveByUserId(Long userid,UserDetailEntity partUserDetail) {
        UserEntity user = manageUserService.QueryUserByIdOption(userid);
        UserDetailEntity userDetail = new UserDetailEntity();

        partUserDetail.setUsername(user.getUsername());
        partUserDetail.setEmail(user.getEmail());
        return null;
    }
}
