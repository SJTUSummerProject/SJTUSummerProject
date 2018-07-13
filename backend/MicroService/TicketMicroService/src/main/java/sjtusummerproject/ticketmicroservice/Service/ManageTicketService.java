package sjtusummerproject.ticketmicroservice.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sjtusummerproject.ticketmicroservice.DataModel.Domain.TicketEntity;

import java.util.List;

public interface ManageTicketService {
    /* page */
    public Page<TicketEntity> QueryTicketPageOptionShow(Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByType(String type, Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByCity(String city, Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByDateRange(String firstDate,String secondDate,Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByPriceRange(double lowPrice, double highPrice,Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByCityAndDateRange(String city, String firstDate, String secondDate, Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByCityAndPriceRange(String city, double lowPrice, double highPrice, Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByCityAndPriceRangeAndDateRange(String city, double lowPrice, double highPrice, String firstDate, String secondDate, Pageable pageable);
    /* no page */
    public List<TicketEntity> QueryTicketOptionByExactDate(String date);
    public List<TicketEntity> QueryTicketOptionByDateRange(String firstDate,String secondDate);
    public List<TicketEntity> QueryTicketOptionByCity(String City);
    public List<TicketEntity> QueryTicketOptionByType(String type);

    public List<TicketEntity> QueryTicketOptionByPriceRange(double firstPrice,double secondPrice);
    public TicketEntity QueryTicketOptionById(Long id);

    public String AddTicketsOptionByJson(String ticketsInfo, String type);
    public String AddTicketOptionByString(String ticketsInfo, String type);

    public String DeleteTicketOptionByExactDate(String date);
    public String DeleteTicketOptionByDateRange(String firstDate,String secondDate);
    public String DeleteTicketOptionByCity(String city);
    public String DeleteTicketOptionByType(String type);
    public String DeleteTicketOptionByPriceRange(String firstPrice,String secondPrice);
    public String DeleteTicketOptionByPriceRange(int firstPrice,int secondPrice);
    public String DeleteTicketOptionByPriceRange(Long firstPrice,Long secondPrice);

    public String UpdateTicketEntireOptionByIdFromEntity(Long Id,TicketEntity ticketEntity);

    public String UpdateTicketTypeOptionByIdFromEntity(Long Id,TicketEntity ticketEntity);
    public String UpdateTicketTypeOptionByIdFromType(Long Id,String type);

    public String UpdateTicketDateOptionByIdFromEntity(Long Id,TicketEntity ticketEntity);
    public String UpdateTicketDateOptionByIdFromType(Long Id,String date);

    public String UpdateTicketCityOptionByIdFromEntity(Long Id,TicketEntity ticketEntity);
    public String UpdateTicketCityOptionByIdFromType(Long Id,String city);

    public String UpdateTicketVenueOptionByIdFromEntity(Long Id,TicketEntity ticketEntity);
    public String UpdateTicketVenueOptionByIdFromType(Long Id,String venue);

    public String UpdateTicketTitleOptionByIdFromEntity(Long Id,TicketEntity ticketEntity);
    public String UpdateTicketTitleOptionByIdFromTitle(Long Id,String title);

    public String UpdateTicketImageOptionByIdFromEntity(Long Id,TicketEntity ticketEntity);
    public String UpdateTicketImageOptionByIdFromTitle(Long Id,String image);

    public String UpdateTicketIntroOptionByIdFromEntity(Long Id,TicketEntity ticketEntity);
    public String UpdateTicketIntroOptionByIdFromTitle(Long Id,String intro);

    public String UpdateTicketStockOptionByIdFromEntity(Long Id,TicketEntity ticketEntity);
    public String UpdateTicketStockOptionByIdFromTitle(Long Id,Long stock);

    public String UpdateTicketLowPriceOptionByIdFromEntity(Long Id,TicketEntity ticketEntity);
    public String UpdateTicketLowPriceOptionByIdFromTitle(Long Id,int lowprice);

    public String UpdateTicketHighPriceOptionByIdFromEntity(Long Id,TicketEntity ticketEntity);
    public String UpdateTicketHighPriceOptionByIdFromTitle(Long Id,int highprice);
}
