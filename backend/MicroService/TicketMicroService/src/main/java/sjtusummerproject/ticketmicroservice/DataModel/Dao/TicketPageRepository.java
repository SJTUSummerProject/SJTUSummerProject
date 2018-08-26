package sjtusummerproject.ticketmicroservice.DataModel.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import sjtusummerproject.ticketmicroservice.DataModel.Domain.TicketEntity;

import java.util.Date;

public interface TicketPageRepository extends PagingAndSortingRepository<TicketEntity, Long> {
    public Page<TicketEntity> findAllAndStatus(int status,Pageable pageable);
    public Page<TicketEntity> findAllByTitleLikeAndStatus(String title, int status, Pageable pageable);
    public Page<TicketEntity> findAllByTypeAndStatus(String type,int status,Pageable pageable);
    public Page<TicketEntity> findAllByCityAndStatus(String city,int status,Pageable pageable);
    public Page<TicketEntity> findAllByCityAndTypeAndStatus(String city, String type, int status, Pageable pageable);
    public Page<TicketEntity> findAllByCityLikeAndTypeLikeAndTitleLikeAndStatus(String city, String type, String title, int status, Pageable pageable);
    public Page<TicketEntity> findAllByStartDateBetweenAndStatusOrEndDateBetweenAndStatus(Date firstDate1, Date secondDate1, int status1, Date firstDate2, Date secondDate2, int status2, Pageable pageable);
    public Page<TicketEntity> findAllByLowpriceBetweenAndStatusOrHighpriceBetweenAndStatus(double lowPrice1,double highPrice1,int status1, double lowPrice2, double highPrice2, int status2,Pageable pageable);
    public Page<TicketEntity> findAllByCityAndStartDateBetweenAndStatusOrCityAndEndDateBetweenAndStatus(String city1,Date firstDate1,Date secondDate1,int status1,String city2,Date firstDate2, Date secondDate2, int status2,Pageable pageable);
    public Page<TicketEntity> findAllByCityAndLowpriceBetweenAndStatusOrCityAndHighpriceBetweenAndStatus(String city1,double lowPrice1,double highPrice1, int status1,String city2, double lowPrice2, double highPrice2, int status2,Pageable pageable);
    public Page<TicketEntity> findAllByCityAndLowpriceBetweenAndStartDateBetweenAndStatusOrCityAndLowpriceBetweenAndEndDateBetweenAndStatusOrCityAndHighpriceBetweenAndStartDateBetweenAndStatusOrCityAndHighpriceBetweenAndEndDateBetweenAndStatus(String city1,double lowPrice1,double highPrice1,Date firstDate1,Date secondDate1,int status1, String city2,double lowPrice2, double highPrice2,Date firstDate2, Date secondDate2,int status2,String city3,double lowPrice3,double highPrice3, Date firstDate3, Date secondDate3,int status3,String city4,double lowPrice4,double highPrice4, Date firstDate4, Date secondDate4,int status4,Pageable pageable);

    /********************************************************************************/
    /** add type **/
    public Page<TicketEntity> findAllByTypeAndCityAndStatus(String type,String city,int status, Pageable pageable);
    public Page<TicketEntity> findAllByTypeAndStartDateBetweenAndStatusOrTypeAndEndDateBetweenAndStatus(String type1,Date firstDate1, Date secondDate1,int status1, String type2, Date firstDate2, Date secondDate2, int status2,Pageable pageable);
    public Page<TicketEntity> findAllByTypeAndLowpriceBetweenAndStatusOrTypeAndHighpriceBetweenAndStatus(String type1,double lowPrice1,double highPrice1, int status1 ,String type2,double lowPrice2, double highPrice2,int status2, Pageable pageable);
    public Page<TicketEntity> findAllByTypeAndCityAndStartDateBetweenAndStatusOrTypeAndCityAndEndDateBetweenAndStatus(String type1,String city1,Date firstDate1,Date secondDate1,int status1,String type2,String city2,Date firstDate2, Date secondDate2,int status2, Pageable pageable);
    public Page<TicketEntity> findAllByTypeAndCityAndLowpriceBetweenAndStatusOrTypeAndCityAndHighpriceBetweenAndStatus(String type1,String city1,double lowPrice1,double highPrice1,int status1,String type2,String city2, double lowPrice2, double highPrice2, int status2,Pageable pageable);
    public Page<TicketEntity> findAllByTypeAndCityAndLowpriceBetweenAndStartDateBetweenAndStatusOrTypeAndCityAndLowpriceBetweenAndEndDateBetweenAndStatusOrTypeAndCityAndHighpriceBetweenAndStartDateBetweenAndStatusOrTypeAndCityAndHighpriceBetweenAndEndDateBetweenAndStatus(String type1,String city1,double lowPrice1,double highPrice1,Date firstDate1,Date secondDate1,int status1,String type2,String city2,double lowPrice2, double highPrice2,Date firstDate2, Date secondDate2,int status2,String type3,String city3,double lowPrice3,double highPrice3, Date firstDate3, Date secondDate3,int status3,String type4,String city4,double lowPrice4,double highPrice4, Date firstDate4, Date secondDate4,int status4,Pageable pageable);
}
