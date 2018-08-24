package sjtusummerproject.userdetailmicroservice.Service;

import org.springframework.web.multipart.MultipartFile;
import sjtusummerproject.userdetailmicroservice.DataModel.Domain.UserDetailEntity;
import sjtusummerproject.userdetailmicroservice.DataModel.Domain.UserEntity;

public interface ManageUserDetailService {
    public UserDetailEntity saveByUserId(UserEntity userEntity);
    public UserDetailEntity updateByUserId(Long userid, String avatar, String phone, String address, Double account, String nickName);
    public UserDetailEntity queryByUserId(Long userid);
    public Boolean updateAccountMinusById(Long userid, double toMinus);
    public Boolean updateAccountPlusById(Long userid, double toPlus);
    public String saveAvatar(MultipartFile avatar);
}
