package com.example.ticketdailyjob.Job;

import com.example.ticketdailyjob.DataModel.Domain.TicketEntity;
import com.example.ticketdailyjob.DataModel.Dao.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
public class DailyJob {
    @Autowired
    TicketRepository ticketRepository;
    @Transactional
    public void deleteOutTimeTickets(){
        List<TicketEntity> ticketEntities = ticketRepository.findAllByStatus(0);
        Date now = new Date();
        for (TicketEntity ticketEntity: ticketEntities){
            Date endDate = ticketEntity.getEndDate();
            if (endDate.before(now)) {
                ticketEntity.setStatus(1);
                ticketRepository.save(ticketEntity);
            }
        }
    }
}
