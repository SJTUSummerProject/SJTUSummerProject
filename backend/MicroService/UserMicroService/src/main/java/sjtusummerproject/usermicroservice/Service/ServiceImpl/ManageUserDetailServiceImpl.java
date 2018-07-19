package sjtusummerproject.usermicroservice.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sjtusummerproject.usermicroservice.DataModel.Dao.UserDetailRepository;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserDetailEntity;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.usermicroservice.Service.ManageUserDetailService;
import sjtusummerproject.usermicroservice.Service.ManageUserService;

@Service
public class ManageUserDetailServiceImpl implements ManageUserDetailService {

    @Autowired
    ManageUserService manageUserService;
    @Autowired
    UserDetailRepository userDetailRepository;

    @Override
    public UserDetailEntity saveByUserId(Long userid,UserDetailEntity partUserDetail) {
        UserEntity user = manageUserService.QueryUserByIdOption(userid);
        UserDetailEntity userDetail = new UserDetailEntity();

        partUserDetail.setUsername(user.getUsername());
        partUserDetail.setEmail(user.getEmail());

        return userDetailRepository.save(partUserDetail);
    }

    @Override
    public UserDetailEntity updateByUserId(Long userid, String avatar, String phone, String address, String account) {
        UserDetailEntity userDetail = userDetailRepository.findById(userid);
        if(avatar != null)
            userDetail.setAvatar(avatar);
        if(phone != null)
            userDetail.setPhone(phone);
        if(phone != null)
            userDetail.setAddress(address);
        if(account != null)
            userDetail.setAccount(Double.parseDouble(account));
        return userDetailRepository.save(userDetail);
    }

    @Override
    public UserDetailEntity queryByUserId(Long userid) {
        return userDetailRepository.findById(userid);
    }
}
