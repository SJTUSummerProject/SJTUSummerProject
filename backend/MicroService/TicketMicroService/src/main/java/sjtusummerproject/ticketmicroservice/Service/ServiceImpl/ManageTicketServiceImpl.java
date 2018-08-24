package sjtusummerproject.ticketmicroservice.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import sjtusummerproject.ticketmicroservice.DataModel.Dao.TicketPageRepository;
import sjtusummerproject.ticketmicroservice.DataModel.Dao.TicketRepository;
import sjtusummerproject.ticketmicroservice.DataModel.Domain.TicketEntity;
import sjtusummerproject.ticketmicroservice.Service.ManageTicketService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@CacheConfig(cacheNames = "TicketRedis")
@Service
public class ManageTicketServiceImpl implements ManageTicketService {
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketPageRepository ticketPageRepository;

    /********************************************************************************/
    /* page */

    @Cacheable(value="10m", key = "'ShowPage:'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionShow(Pageable pageable) {
        return ticketPageRepository.findAll(pageable);
    }

    @Override
    public Page<TicketEntity> QueryTicketPageOptionByCityAndTypeAndTitle(String city, String type, String title, Pageable pageable) {
        return ticketPageRepository.findAllByCityLikeAndTypeLikeAndTitleLike(city, type, title, pageable);
    }

    @Cacheable(value = "10m", key = "#title+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByTitle(String title, Pageable pageable){
        title = '%'+title.trim()+'%';
        return ticketPageRepository.findAllByTitleLike(title, pageable);
    }
    @Cacheable(value="10m",key = "#type+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByType(String type, Pageable pageable) {
        return ticketPageRepository.findAllByType(type,pageable);
    }

    @Cacheable(value="10m",key = "#city+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByCity(String city, Pageable pageable) {
        return ticketPageRepository.findAllByCity(city,pageable);
    }

    @Cacheable(value="10m", key = "#city+#type+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByCityAndType(String city, String type, Pageable pageable) {
        return ticketPageRepository.findAllByCityAndType(city, type, pageable);
    }


    @Cacheable(value="10m",key = "#firstDateString + #secondDateString+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByDateRange(String firstDateString, String secondDateString, Pageable pageable) {
        Date firstDate = ChangeStringToDate(firstDateString);
        Date secondDate = ChangeStringToDate(secondDateString);

        return ticketPageRepository.findAllByStartDateBetweenOrEndDateBetween(firstDate,secondDate,firstDate,secondDate,pageable);
    }

    @Cacheable(value="10m",key = "#lowPrice + #highPrice+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByPriceRange(double lowPrice, double highPrice, Pageable pageable) {
        return ticketPageRepository.findAllByLowpriceBetweenOrHighpriceBetween(lowPrice,highPrice,lowPrice,highPrice,pageable);
    }

    @Cacheable(value="10m",key = "#city + #firstDateString + #secondDateString+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByCityAndDateRange(String city, String firstDateString, String secondDateString, Pageable pageable) {
        Date firstDate = ChangeStringToDate(firstDateString);
        Date secondDate = ChangeStringToDate(secondDateString);

        return ticketPageRepository.findAllByCityAndStartDateBetweenOrCityAndEndDateBetween(city,firstDate,secondDate,city,firstDate,secondDate,pageable);
    }

    @Cacheable(value="10m",key = "#city + #lowPrice + #highPrice+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByCityAndPriceRange(String city, double lowPrice, double highPrice, Pageable pageable) {
        return ticketPageRepository.findAllByCityAndLowpriceBetweenOrCityAndHighpriceBetween(city,lowPrice,highPrice,city,lowPrice,highPrice,pageable);
    }

    @Cacheable(value="10m",key = "#city + #lowPrice + #highPrice + #firstDateString + #secondDateString+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByCityAndPriceRangeAndDateRange(String city, double lowPrice, double highPrice, String firstDateString, String secondDateString, Pageable pageable) {
        Date firstDate = ChangeStringToDate(firstDateString);
        Date secondDate = ChangeStringToDate(secondDateString);
        return ticketPageRepository.findAllByCityAndLowpriceBetweenAndStartDateBetweenOrCityAndLowpriceBetweenAndEndDateBetweenOrCityAndHighpriceBetweenAndStartDateBetweenOrCityAndHighpriceBetweenAndEndDateBetween(city,lowPrice,highPrice,firstDate,secondDate,city,lowPrice,highPrice,firstDate,secondDate,city,lowPrice,highPrice,firstDate,secondDate,city,lowPrice,highPrice,firstDate,secondDate,pageable);
    }
    /********************************************************************************/
    /** add type **/
    @Cacheable(value="10m",key = "#type + #city+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByTypeAndCity(String type, String city, Pageable pageable) {
        return ticketPageRepository.findAllByTypeAndCity(type,city,pageable);
    }

    @Cacheable(value="10m",key = "#type + #firstDateString + #secondDateString+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByTypeAndDateRange(String type, String firstDateString, String secondDateString, Pageable pageable) {
        Date firstDate = ChangeStringToDate(firstDateString);
        Date secondDate = ChangeStringToDate(secondDateString);
        return ticketPageRepository.findAllByTypeAndStartDateBetweenOrTypeAndEndDateBetween(type,firstDate,secondDate,type,firstDate,secondDate,pageable);
    }

    @Cacheable(value="10m",key = "#type + #lowPrice + #highPrice+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByTypeAndPriceRange(String type, double lowPrice, double highPrice, Pageable pageable) {
        return ticketPageRepository.findAllByTypeAndLowpriceBetweenOrTypeAndHighpriceBetween(type,lowPrice,highPrice,type,lowPrice,highPrice,pageable);
    }

    @Cacheable(value="10m",key = "#type + #city + #firstDateString + #secondDateString+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByTypeAndCityAndDateRange(String type, String city, String firstDateString, String secondDateString, Pageable pageable) {
        Date firstDate = ChangeStringToDate(firstDateString);
        Date secondDate = ChangeStringToDate(secondDateString);
        return ticketPageRepository.findAllByTypeAndCityAndStartDateBetweenOrTypeAndCityAndEndDateBetween(type,city,firstDate,secondDate,type,city,firstDate,secondDate,pageable);
    }

    @Cacheable(value="10m",key = "#type + #city + #lowPrice + #highPrice+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByTypeAndCityAndPriceRange(String type, String city, double lowPrice, double highPrice, Pageable pageable) {
        return ticketPageRepository.findAllByTypeAndCityAndLowpriceBetweenOrTypeAndCityAndHighpriceBetween(type,city,lowPrice,highPrice,type,city,lowPrice,highPrice,pageable);
    }

    @Cacheable(value="10m",key = "#type + #city + #lowPrice + #highPrice + #firstDateString + #secondDateString+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByTypeAndCityAndPriceRangeAndDateRange(String type, String city, double lowPrice, double highPrice, String firstDateString, String secondDateString, Pageable pageable) {
        Date firstDate = ChangeStringToDate(firstDateString);
        Date secondDate = ChangeStringToDate(secondDateString);

        return ticketPageRepository.findAllByTypeAndCityAndLowpriceBetweenAndStartDateBetweenOrTypeAndCityAndLowpriceBetweenAndEndDateBetweenOrTypeAndCityAndHighpriceBetweenAndStartDateBetweenOrTypeAndCityAndHighpriceBetweenAndEndDateBetween(type,city,lowPrice,highPrice,firstDate,secondDate,type,city,lowPrice,highPrice,firstDate,secondDate,type,city,lowPrice,highPrice,firstDate,secondDate,type,city,lowPrice,highPrice,firstDate,secondDate,pageable);
    }

    /********************************************************************************/
    /* no page */
    @Override

    @Cacheable(value="10m", key = "'id:'+#id")
    public TicketEntity QueryTicketOptionById(Long id) {
        TicketEntity ticketEntity = ticketRepository.findById(id);
        return ticketEntity;
    }

    @Override
    public TicketEntity QueryTicketById(Long id){
        return ticketRepository.findById(id);
    }
    @Override
    public List<TicketEntity> QueryTicketOptionByBatchIds(String ids) {
        /*
        * ids in form
        * [1,2,3,4,5,6]
        * */
        String[] idSplit = ids.trim().replace("[","").replace("]","").split(",");
        List<TicketEntity> res = new LinkedList<>();
        for(String eachId : idSplit){
            res.add(QueryTicketOptionById(Long.parseLong(eachId.trim())));
        }
        return res;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Boolean updateStockMinusById(Long id, Long toMinus) {
        TicketEntity ticket = ticketRepository.findById(id);
        Long stock = ticket.getStock();
        if(stock < toMinus)
            return false;
        ticket.setStock(stock-toMinus);
        ticketRepository.save(ticket);
        return true;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Boolean updateStockPlusById(Long id, Long toPlus) {
        TicketEntity ticket = ticketRepository.findById(id);
        ticket.setStock(ticket.getStock()+toPlus);
        ticketRepository.save(ticket);
        return true;
    }

    /********************************************************************************/
    /** 自定义内部函数 **/

    /* Change String-form:2018-09-09 to Date */
    public Date ChangeStringToDate(String dateString){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(dateString);
            return date;
        }catch (Exception e){
            System.out.println("ChangeStringToDate 崩了");
            return null;
        }
    }
}
