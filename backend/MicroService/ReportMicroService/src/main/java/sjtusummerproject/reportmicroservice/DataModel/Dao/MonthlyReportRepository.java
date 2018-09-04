package sjtusummerproject.reportmicroservice.DataModel.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import sjtusummerproject.reportmicroservice.DataModel.Domain.MonthlyReportEntity;

import java.awt.print.Pageable;

public interface MonthlyReportRepository extends CrudRepository<MonthlyReportEntity,Long>{
    MonthlyReportEntity queryByTicketIdAndYearAndMonth(Long ticketid, int year, int month);
    Page<MonthlyReportEntity> queryByCityAndYearAndMonth(Long tickid, int year, int month, Pageable pageable);
}
