package sjtusummerproject.reportmicroservice.DataModel.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import sjtusummerproject.reportmicroservice.DataModel.Domain.DailyReportEntity;

import java.util.Date;

public interface DailyReportRepository extends CrudRepository<DailyReportEntity,Long> {
    public Page<DailyReportEntity> findAll(Pageable pageable);
    public DailyReportEntity findAllByTicketIdAndDate(Long ticketid, Date date);
    public Page<DailyReportEntity> findAllByCityAndDate(String city, Date date, Pageable pageable);
}