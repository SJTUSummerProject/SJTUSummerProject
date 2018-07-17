package sjtusummerproject.ticketmicroservice.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sjtusummerproject.ticketmicroservice.DataModel.Domain.TicketEntity;
import sjtusummerproject.ticketmicroservice.Service.FilterTicketService;
import sjtusummerproject.ticketmicroservice.Service.ManageTicketService;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class FilterTicketServiceImpl implements FilterTicketService{
    /*
     * if lowprice <= ticketEntity.lowprice <= highprice ||
     *    lowprice <= ticketEntity.highprice <= highprice
     *    return true;
     * else
     *    return false;
     * */
    public boolean ComparePrice(double lowprice,double highprice, TicketEntity ticketEntity){
        double entityLowPrice = ticketEntity.getLowprice();
        double entityHighPrice = ticketEntity.getHighprice();

        if(lowprice<=entityLowPrice && entityLowPrice<=highprice)
            return true;
        else if(lowprice<=entityHighPrice&&entityHighPrice<=highprice)
            return true;
        else
            return false;
    }
}
