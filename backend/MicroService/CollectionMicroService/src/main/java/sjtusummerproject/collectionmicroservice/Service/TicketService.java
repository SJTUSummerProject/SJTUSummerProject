package sjtusummerproject.collectionmicroservice.Service;

import org.springframework.web.client.RestTemplate;
import sjtusummerproject.collectionmicroservice.DataModel.Domain.TicketEntity;

public interface TicketService {
    public TicketEntity queryTicketById(Long id);
    void setRestTemplate(RestTemplate restTemplate);
}
