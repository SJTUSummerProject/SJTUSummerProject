package com.sjtusummerproject.cartmicroservice.Service;

import com.sjtusummerproject.cartmicroservice.DataModel.Domain.TicketEntity;
import org.springframework.web.client.RestTemplate;

public interface TicketService {
    public TicketEntity queryTicketById(Long id);
    void setRestTemplate(RestTemplate restTemplate);
}
