package com.sjtusummerproject.ordermicroservice.Service;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.TicketEntity;

public interface TicketService {
    public TicketEntity queryTicketById(Long id);
}
