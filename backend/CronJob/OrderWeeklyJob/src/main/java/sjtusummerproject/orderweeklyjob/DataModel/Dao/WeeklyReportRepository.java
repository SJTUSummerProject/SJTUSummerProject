package sjtusummerproject.orderweeklyjob.DataModel.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import sjtusummerproject.orderweeklyjob.DataModel.Domain.WeeklyReportEntity;

public interface WeeklyReportRepository extends CrudRepository<WeeklyReportEntity,Long>{
    public WeeklyReportEntity queryByTicketIdAndYearAndMonthAndWeek(Long ticketid, int year, int month, int week);
    public Page<WeeklyReportEntity> queryByCityAndYearAndMonthAndWeek(String city, int year, int month, int week, Pageable pageable);
}
