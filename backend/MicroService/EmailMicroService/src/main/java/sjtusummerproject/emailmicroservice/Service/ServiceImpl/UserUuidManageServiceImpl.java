package sjtusummerproject.emailmicroservice.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sjtusummerproject.emailmicroservice.DataModel.Dao.UserUuidRepository;
import sjtusummerproject.emailmicroservice.DataModel.Domain.UserUuidEntity;
import sjtusummerproject.emailmicroservice.Service.UserUuidManageService;

@Service
public class UserUuidManageServiceImpl implements UserUuidManageService {
    @Autowired
    UserUuidRepository userUuidRepository;

    @Override
    public String AddUserUuidService(UserUuidEntity userUuidEntity) {
        userUuidRepository.save(userUuidEntity);
        return "ok";
    }

    @Override
    public UserUuidEntity QueryUserUuidService(String uuid) {
        UserUuidEntity userUuidEntity = userUuidRepository.findByUuid(uuid);
        return userUuidEntity;
    }
}
