package com.example.orderdailyjob.DataModel.Dao;

import com.example.orderdailyjob.DataModel.Domain.WeeklyReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface WeeklyReportRepository extends CrudRepository<WeeklyReportEntity,Long>{
    public WeeklyReportEntity queryByTicketIdAndYearAndMonthAndWeek(Long ticketid, int year, int month, int week);
    public Page<WeeklyReportEntity> queryByCityAndYearAndMonthAndWeek(String city, int year, int month, int week, Pageable pageable);
}
