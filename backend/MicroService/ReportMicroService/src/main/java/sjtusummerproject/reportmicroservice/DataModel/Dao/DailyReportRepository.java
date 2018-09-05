package sjtusummerproject.reportmicroservice.DataModel.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import sjtusummerproject.reportmicroservice.DataModel.Domain.DailyReportEntity;

import java.util.Date;

public interface DailyReportRepository extends CrudRepository<DailyReportEntity,Long> {
    public DailyReportEntity queryByTicketIdAndDate(Long ticketid, Date date);
    public Page<DailyReportEntity> queryByCityAndDate(String city, Date date, Pageable pageable);
}