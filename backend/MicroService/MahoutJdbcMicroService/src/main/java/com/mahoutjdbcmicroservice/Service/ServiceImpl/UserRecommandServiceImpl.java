package com.mahoutjdbcmicroservice.Service.ServiceImpl;

import com.mahoutjdbcmicroservice.DataModel.Dao.UserRecommendRepository;
import com.mahoutjdbcmicroservice.DataModel.Domain.UserRecommendEntity;
import com.mahoutjdbcmicroservice.Service.UserRecommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRecommandServiceImpl implements UserRecommandService {
    @Autowired
    UserRecommendRepository userRecommendRepository;

    @Override
    public void save(UserRecommendEntity userRecommendEntity) {
        userRecommendRepository.save(userRecommendEntity);
    }

    @Override
    public List<Long> queryTicketByUserid(Long userId) {
        UserRecommendEntity userRecommendEntity = userRecommendRepository.findById(userId);
        if(userRecommendEntity == null)
            return null;
        return userRecommendEntity.getTicketRecommends();
    }
}
