package sjtusummerproject.usermicroservice.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import sjtusummerproject.usermicroservice.DataModel.Dao.UserDetailRepository;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserDetailEntity;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.usermicroservice.Service.ManageUserDetailService;
import sjtusummerproject.usermicroservice.Service.ManageUserService;

import java.util.UUID;


@Service
public class ManageUserDetailServiceImpl implements ManageUserDetailService {

    @Autowired
    ManageUserService manageUserService;
    @Autowired
    UserDetailRepository userDetailRepository;
    @Autowired
    RestTemplate restTemplate;

    @Value("${imgservice.url}")
    String imgServiceUrl;

    @Override
    public UserDetailEntity saveByUserId(Long userid,UserDetailEntity partUserDetail) {
        UserEntity user = manageUserService.QueryUserByIdOption(userid);
        UserDetailEntity userDetail = new UserDetailEntity();

    //    partUserDetail.setUsername(user.getUsername());
     //   partUserDetail.setEmail(user.getEmail());

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
            if (avatar == null) return null;
            byte[] img = avatar.getBytes();
            UUID uuid = UUID.randomUUID();
            String token = uuid.toString();
            MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
            multiValueMap.add("uuid", token);
            multiValueMap.add("img", img);
            String url = restTemplate.postForObject(imgServiceUrl, multiValueMap, String.class);
            return url;
        }
        catch (Exception e){
            return null;
        }
    }
}
