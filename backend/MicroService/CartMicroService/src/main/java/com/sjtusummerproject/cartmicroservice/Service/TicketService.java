package com.sjtusummerproject.cartmicroservice.Service;

import com.sjtusummerproject.cartmicroservice.DataModel.Domain.TicketEntity;

public interface TicketService {
    public TicketEntity queryTicket(Long id);
}
