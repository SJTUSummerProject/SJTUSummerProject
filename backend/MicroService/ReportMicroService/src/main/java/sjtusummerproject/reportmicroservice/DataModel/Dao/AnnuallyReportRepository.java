package sjtusummerproject.reportmicroservice.DataModel.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import sjtusummerproject.reportmicroservice.DataModel.Domain.AnnuallyReportEntity;

public interface AnnuallyReportRepository extends CrudRepository<AnnuallyReportEntity,Long>{
    public Page<AnnuallyReportEntity> findAll(Pageable pageable);
    public AnnuallyReportEntity findAllByTicketIdAndYear(Long ticketid, int year);
    public Page<AnnuallyReportEntity> findAllByCityAndYear(String city, int year, Pageable pageable);
}
