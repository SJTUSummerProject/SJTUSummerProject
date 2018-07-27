package sjtusummerproject.ticketmicroservice.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sjtusummerproject.ticketmicroservice.DataModel.Domain.TicketEntity;

import java.util.Date;
import java.util.List;

public interface ManageTicketService {
    /* page */
    public Page<TicketEntity> QueryTicketPageOptionShow(Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByTitle(String title, Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByType(String type, Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByCity(String city, Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByCityAndType(String city, String type, Pageable pageable) ;
    public Page<TicketEntity> QueryTicketPageOptionByCityAndTypeAndTitle(String city, String type, String title, Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByDateRange(String firstDate,String secondDate,Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByPriceRange(double lowPrice, double highPrice,Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByCityAndDateRange(String city, String firstDate, String secondDate, Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByCityAndPriceRange(String city, double lowPrice, double highPrice, Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByCityAndPriceRangeAndDateRange(String city, double lowPrice, double highPrice, String firstDate, String secondDate, Pageable pageable);
    /******************************/
    /** add type **/
    public Page<TicketEntity> QueryTicketPageOptionByTypeAndCity(String type,String city, Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByTypeAndDateRange(String type, String firstDate,String secondDate,Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByTypeAndPriceRange(String type, double lowPrice, double highPrice,Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByTypeAndCityAndDateRange(String type, String city, String firstDate, String secondDate, Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByTypeAndCityAndPriceRange(String type, String city, double lowPrice, double highPrice, Pageable pageable);
    public Page<TicketEntity> QueryTicketPageOptionByTypeAndCityAndPriceRangeAndDateRange(String type, String city,double lowPrice, double highPrice,String firstDate, String secondDate, Pageable pageable);
    /* no page */

    public TicketEntity QueryTicketById(Long id);
    public TicketEntity QueryTicketOptionById(Long id);
    public List<TicketEntity> QueryTicketOptionByBatchIds(String ids);

    public Boolean updateStockMinusById(Long id, Long toMinus);
    public Boolean updateStockPlusById(Long id, Long toPlus);
    public Date ChangeStringToDate(String dateString);
}
