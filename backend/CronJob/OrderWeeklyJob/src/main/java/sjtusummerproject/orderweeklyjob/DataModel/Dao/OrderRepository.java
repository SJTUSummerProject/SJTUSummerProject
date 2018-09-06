package sjtusummerproject.orderweeklyjob.DataModel.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sjtusummerproject.orderweeklyjob.DataModel.Domain.OrderEntity;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    public List<OrderEntity> findAllByOrderTimeEqualsAndStatusLike(Date date, String status);
    public List<OrderEntity> findAllByOrderTimeBetweenAndStatusLike(Date startDate, Date endDate, String status);
}
