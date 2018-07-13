package sjtusummerproject.ticketmicroservice.DataModel.Dao;

import org.springframework.data.repository.CrudRepository;
import sjtusummerproject.ticketmicroservice.DataModel.Domain.TicketEntity;

import java.util.List;

public interface TicketRepository extends CrudRepository<TicketEntity,Long>{
    public TicketEntity findById(Long id);
//    public List<TicketEntity> findAllByDate(String date);
    public List<TicketEntity> findAll();
    public List<TicketEntity> findAllByCity(String city);
    public List<TicketEntity> findAllByType(String type);
}
