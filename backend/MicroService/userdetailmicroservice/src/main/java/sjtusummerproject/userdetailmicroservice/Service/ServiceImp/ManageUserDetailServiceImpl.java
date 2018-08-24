package sjtusummerproject.userdetailmicroservice.Service.ServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sjtusummerproject.userdetailmicroservice.DataModel.Dao.PictureRepository;
import sjtusummerproject.userdetailmicroservice.DataModel.Dao.UserDetailRepository;
import sjtusummerproject.userdetailmicroservice.DataModel.Domain.PictureEntity;
import sjtusummerproject.userdetailmicroservice.DataModel.Domain.UserDetailEntity;
import sjtusummerproject.userdetailmicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.userdetailmicroservice.Service.ManageUserDetailService;

import java.util.UUID;


@Service
public class ManageUserDetailServiceImpl implements ManageUserDetailService {

    @Autowired
    UserDetailRepository userDetailRepository;
    @Autowired
    PictureRepository pictureRepository;

    @Value("${imgservice.url}")
    String imgServiceUrl;

    @Override
    public UserDetailEntity saveByUserId(UserEntity user) {
        UserDetailEntity partUserDetail = new UserDetailEntity();
        partUserDetail.setId(user.getId());
        partUserDetail.setUsername(user.getUsername());
        partUserDetail.setEmail(user.getEmail());
        return userDetailRepository.save(partUserDetail);
    }



    @Override
    public UserDetailEntity updateByUserId(Long userid, String avatar, String phone, String address, Double account, String nickName) {
        UserDetailEntity userDetail = userDetailRepository.findById(userid);
        if (userDetail == null) return null;
        if(avatar != null)
            userDetail.setAvatar(avatar);
        if(phone != null)
            userDetail.setPhone(phone);
        if(phone != null)
            userDetail.setAddress(address);
        if(account != null)
            userDetail.setAccount(account);
        if(nickName != null)
            userDetail.setNickName(nickName);
        return userDetailRepository.save(userDetail);
    }

    @Override
    public UserDetailEntity queryByUserId(Long userid) {
        return userDetailRepository.findById(userid);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Boolean updateAccountMinusById(Long userid, double toMinus) {
        UserDetailEntity userDetail = userDetailRepository.findById(userid);
        userDetail.setAccount(userDetail.getAccount()-toMinus);
        userDetailRepository.save(userDetail);
        return true;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Boolean updateAccountPlusById(Long userid, double toPlus) {
        UserDetailEntity userDetail = userDetailRepository.findById(userid);
        userDetail.setAccount(userDetail.getAccount()+toPlus);
        userDetailRepository.save(userDetail);
        return true;
    }

    @Override
    public String saveAvatar(MultipartFile avatar){
        try {
            if (avatar == null) {
                return null;
            }
            UUID uuid = UUID.randomUUID();
            String id = uuid.toString();
            PictureEntity pictureEntity = new PictureEntity();
            pictureEntity.setUuid(id);
            pictureEntity.setBase64(avatar.getBytes());
            pictureRepository.save(pictureEntity);
            return imgServiceUrl+uuid;
        }
        catch (Exception e){
            return null;
        }
    }
}
