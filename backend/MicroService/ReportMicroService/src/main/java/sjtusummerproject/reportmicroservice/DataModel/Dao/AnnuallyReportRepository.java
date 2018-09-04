package sjtusummerproject.reportmicroservice.DataModel.Dao;

import org.springframework.data.repository.CrudRepository;
import sjtusummerproject.reportmicroservice.DataModel.Domain.AnnuallyReportEntity;

public interface AnnuallyReportRepository extends CrudRepository<AnnuallyReportEntity,Long>{
}
