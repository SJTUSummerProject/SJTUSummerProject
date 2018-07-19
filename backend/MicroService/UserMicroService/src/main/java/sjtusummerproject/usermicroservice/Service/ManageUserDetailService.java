package sjtusummerproject.usermicroservice.Service;

import org.springframework.web.multipart.MultipartFile;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserDetailEntity;

public interface ManageUserDetailService {
    public UserDetailEntity saveByUserId(Long userid,UserDetailEntity partUserDetail);
    public UserDetailEntity updateByUserId(Long userid,String avatar,String phone,String address,String account);
    public UserDetailEntity queryByUserId(Long userid);
    public Boolean updateAccountMinusById(Long userid, double toMinus);
    public Boolean updateAccountPlusById(Long userid, double toPlus);
    public String saveAvatar(MultipartFile avatar);
}
