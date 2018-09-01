package sjtusummerproject.reportmicroservice.DataModel.Dao;

import org.springframework.data.repository.CrudRepository;
import sjtusummerproject.reportmicroservice.DataModel.Domain.ReportEntity;

public interface ReportRepository extends CrudRepository<ReportEntity,Long> {
}