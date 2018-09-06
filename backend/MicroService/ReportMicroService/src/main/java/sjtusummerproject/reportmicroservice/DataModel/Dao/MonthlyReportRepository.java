package sjtusummerproject.reportmicroservice.DataModel.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import sjtusummerproject.reportmicroservice.DataModel.Domain.MonthlyReportEntity;


public interface MonthlyReportRepository extends CrudRepository<MonthlyReportEntity,Long>{
    Page<MonthlyReportEntity> queryAll(Pageable pageable);
    MonthlyReportEntity queryByTicketIdAndYearAndMonth(Long ticketid, int year, int month);
    Page<MonthlyReportEntity> queryByCityAndYearAndMonth(String city, int year, int month, Pageable pageable);
}
