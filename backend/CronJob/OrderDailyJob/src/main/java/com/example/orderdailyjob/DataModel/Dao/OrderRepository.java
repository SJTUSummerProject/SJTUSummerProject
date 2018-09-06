package com.example.orderdailyjob.DataModel.Dao;

import com.example.orderdailyjob.DataModel.Domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    public List<OrderEntity> findAllByOrderTimeEqualsAndStatusLike(Date date, String status);
<<<<<<< HEAD
=======
    public List<OrderEntity> findAllByOrderTimeBetweenAndStatusLike(Date startDate,Date endDate, String status);
>>>>>>> 0b1687bc8b558f2a862af172888e2a8f27078832
}
