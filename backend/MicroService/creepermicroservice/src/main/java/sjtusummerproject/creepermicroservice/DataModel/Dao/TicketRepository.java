package sjtusummerproject.creepermicroservice.DataModel.Dao;

import org.springframework.data.repository.CrudRepository;
import sjtusummerproject.creepermicroservice.DataModel.Domain.TicketEntity;

public interface TicketRepository extends CrudRepository<TicketEntity,Long>{
}
