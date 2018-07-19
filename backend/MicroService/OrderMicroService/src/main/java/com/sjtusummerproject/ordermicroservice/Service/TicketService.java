package com.sjtusummerproject.ordermicroservice.Service;

import com.sjtusummerproject.ordermicroservice.DataModel.Domain.TicketEntity;

import java.util.List;

public interface TicketService {
    public TicketEntity queryTicketById(Long id);
    public List<TicketEntity> queryTicketByBatchIds(String ids);
    public Boolean updateStockMinus(Long id, Long toMinus);
    public Boolean updateStockPlus(Long id, Long toPlus);
}
