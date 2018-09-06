package sjtusummerproject.orderannuallyjob.DataModel.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sjtusummerproject.orderannuallyjob.DataModel.Domain.OrderEntity;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    public List<OrderEntity> findAllByOrderTimeEqualsAndStatusLike(Date date, String status);
    public List<OrderEntity> findAllByOrderTimeBetweenAndStatusLike(Date startDate, Date endDate, String status);
}
