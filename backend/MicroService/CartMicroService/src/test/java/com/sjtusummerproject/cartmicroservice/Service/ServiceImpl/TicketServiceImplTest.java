package com.sjtusummerproject.cartmicroservice.Service.ServiceImpl;

import com.sjtusummerproject.cartmicroservice.CartmicroserviceApplicationTests;
import com.sjtusummerproject.cartmicroservice.DataModel.Domain.TicketEntity;
import com.sjtusummerproject.cartmicroservice.Mock.TicketTemplateMock;
import com.sjtusummerproject.cartmicroservice.Service.TicketService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class TicketServiceImplTest extends CartmicroserviceApplicationTests {
    @Autowired
    TicketService ticketService;
    @Autowired
    TicketTemplateMock ticketTemplateMock;
    @Test
    public void queryTicketById() {
        ticketService.setRestTemplate(ticketTemplateMock);
        TicketEntity ticketEntity = ticketService.queryTicketById(1l);
        assertEquals(1l, (long)ticketEntity.getId());
    }
}