package com.example.ticketdailyjob.DataModel.Domain;

import com.example.ticketdailyjob.DataModel.Dao.TicketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketRepository extends CrudRepository<TicketEntity,Long> {
    public List<TicketEntity> findAllByStatus(int status);
}
