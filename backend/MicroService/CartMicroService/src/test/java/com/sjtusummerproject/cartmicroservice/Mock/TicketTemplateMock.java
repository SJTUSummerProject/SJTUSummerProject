package com.sjtusummerproject.cartmicroservice.Mock;

import com.sjtusummerproject.cartmicroservice.DataModel.Domain.TicketEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class TicketTemplateMock extends RestTemplate {
    @Override
    public <T> T getForObject(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setId(1l);
        return (T) ticketEntity;
    }
}
