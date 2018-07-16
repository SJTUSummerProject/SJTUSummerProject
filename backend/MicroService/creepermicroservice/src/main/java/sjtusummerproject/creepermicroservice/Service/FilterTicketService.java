
import sjtusummerproject.ticketmicroservice.DataModel.Domain.TicketEntity;

import java.util.List;

public interface FilterTicketService {
    public List<TicketEntity> FilterTicketOptionByExactDate(List<TicketEntity> list, String date);
    public List<TicketEntity> FilterTicketOptionByDateRange(List<TicketEntity> list, String firstdate, String seconddate);
    public List<TicketEntity> FilterTicketOptionByPriceRange(List<TicketEntity> list, double lowprice, double highprice);
}
