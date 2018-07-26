package sjtusummerproject.ticketmicroservice.DataModel.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import sjtusummerproject.ticketmicroservice.DataModel.Domain.TicketEntity;

import java.util.Date;

public interface TicketPageRepository extends PagingAndSortingRepository<TicketEntity, Long> {
    public Page<TicketEntity> findAll(Pageable pageable);
    public Page<TicketEntity> findAllByTitleLike(String title, Pageable pageable);
    public Page<TicketEntity> findAllByType(String type,Pageable pageable);
    public Page<TicketEntity> findAllByCity(String city,Pageable pageable);
    public Page<TicketEntity> findAllByCityAndType(String city, String type, Pageable pageable);
    public Page<TicketEntity> findAllByCityLikeAndTypeLikeAndTitleLike(String city, String type, String title, Pageable pageable);
    public Page<TicketEntity> findAllByStartDateBetweenOrEndDateBetween(Date firstDate1, Date secondDate1, Date firstDate2, Date secondDate2, Pageable pageable);
    public Page<TicketEntity> findAllByLowpriceBetweenOrHighpriceBetween(double lowPrice1,double highPrice1, double lowPrice2, double highPrice2, Pageable pageable);
    public Page<TicketEntity> findAllByCityAndStartDateBetweenOrCityAndEndDateBetween(String city1,Date firstDate1,Date secondDate1,String city2,Date firstDate2, Date secondDate2, Pageable pageable);
    public Page<TicketEntity> findAllByCityAndLowpriceBetweenOrCityAndHighpriceBetween(String city1,double lowPrice1,double highPrice1, String city2, double lowPrice2, double highPrice2, Pageable pageable);
    public Page<TicketEntity> findAllByCityAndLowpriceBetweenAndStartDateBetweenOrCityAndLowpriceBetweenAndEndDateBetweenOrCityAndHighpriceBetweenAndStartDateBetweenOrCityAndHighpriceBetweenAndEndDateBetween(String city1,double lowPrice1,double highPrice1,Date firstDate1,Date secondDate1,String city2,double lowPrice2, double highPrice2,Date firstDate2, Date secondDate2,String city3,double lowPrice3,double highPrice3, Date firstDate3, Date secondDate3,String city4,double lowPrice4,double highPrice4, Date firstDate4, Date secondDate4,Pageable pageable);

    /********************************************************************************/
    /** add type **/
    public Page<TicketEntity> findAllByTypeAndCity(String type,String city,Pageable pageable);
    public Page<TicketEntity> findAllByTypeAndStartDateBetweenOrTypeAndEndDateBetween(String type1,Date firstDate1, Date secondDate1,String type2, Date firstDate2, Date secondDate2, Pageable pageable);
    public Page<TicketEntity> findAllByTypeAndLowpriceBetweenOrTypeAndHighpriceBetween(String type1,double lowPrice1,double highPrice1, String type2,double lowPrice2, double highPrice2, Pageable pageable);
    public Page<TicketEntity> findAllByTypeAndCityAndStartDateBetweenOrTypeAndCityAndEndDateBetween(String type1,String city1,Date firstDate1,Date secondDate1,String type2,String city2,Date firstDate2, Date secondDate2, Pageable pageable);
    public Page<TicketEntity> findAllByTypeAndCityAndLowpriceBetweenOrTypeAndCityAndHighpriceBetween(String type1,String city1,double lowPrice1,double highPrice1,String type2,String city2, double lowPrice2, double highPrice2, Pageable pageable);
    public Page<TicketEntity> findAllByTypeAndCityAndLowpriceBetweenAndStartDateBetweenOrTypeAndCityAndLowpriceBetweenAndEndDateBetweenOrTypeAndCityAndHighpriceBetweenAndStartDateBetweenOrTypeAndCityAndHighpriceBetweenAndEndDateBetween(String type1,String city1,double lowPrice1,double highPrice1,Date firstDate1,Date secondDate1,String type2,String city2,double lowPrice2, double highPrice2,Date firstDate2, Date secondDate2,String type3,String city3,double lowPrice3,double highPrice3, Date firstDate3, Date secondDate3,String type4,String city4,double lowPrice4,double highPrice4, Date firstDate4, Date secondDate4,Pageable pageable);
}
