package sjtusummerproject.ordermonthlyjob.DataModel.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import sjtusummerproject.ordermonthlyjob.DataModel.Domain.MonthlyReportEntity;

public interface MonthlyReportRepository extends CrudRepository<MonthlyReportEntity,Long> {
    MonthlyReportEntity queryByTicketIdAndYearAndMonth(Long ticketid, int year, int month);
    Page<MonthlyReportEntity> queryByCityAndYearAndMonth(String city, int year, int month, Pageable pageable);
}
