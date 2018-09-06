package com.example.orderdailyjob.DataModel.Dao;

import com.example.orderdailyjob.DataModel.Domain.AnnuallyReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface AnnuallyReportRepository extends CrudRepository<AnnuallyReportEntity,Long>{
    public AnnuallyReportEntity queryByTicketIdAndYear(Long ticketid, int year);
    public Page<AnnuallyReportEntity> queryByCityAndYear(String city, int year, Pageable pageable);
}
