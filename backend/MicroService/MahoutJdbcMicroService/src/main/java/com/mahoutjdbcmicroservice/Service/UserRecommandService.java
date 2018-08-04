package com.mahoutjdbcmicroservice.Service;

import com.mahoutjdbcmicroservice.DataModel.Domain.UserRecommendEntity;

import java.util.List;

public interface UserRecommandService {
    public void save(UserRecommendEntity userRecommendEntity);
    public List<Long> queryTicketByUserid(Long userId);
}
