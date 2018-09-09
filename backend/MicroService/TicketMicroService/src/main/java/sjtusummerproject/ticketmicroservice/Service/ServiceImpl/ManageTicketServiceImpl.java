package sjtusummerproject.ticketmicroservice.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sjtusummerproject.ticketmicroservice.DataModel.Dao.PictureRepository;
import sjtusummerproject.ticketmicroservice.DataModel.Dao.TicketPageRepository;
import sjtusummerproject.ticketmicroservice.DataModel.Dao.TicketRepository;
import sjtusummerproject.ticketmicroservice.DataModel.Domain.PictureEntity;
import sjtusummerproject.ticketmicroservice.DataModel.Domain.TicketEntity;
import sjtusummerproject.ticketmicroservice.Service.ManageTicketService;
import sjtusummerproject.ticketmicroservice.Utils.ImgUtils;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.tomcat.util.http.fileupload.IOUtils.copy;

@CacheConfig(cacheNames = "TicketRedis")
@Service
public class ManageTicketServiceImpl implements ManageTicketService {
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketPageRepository ticketPageRepository;

    @Autowired
    PictureRepository pictureRepository;

    @Autowired
    RedisTemplate<?,?> redisTemplate;

    @Value("${imgservice.url}")
    String imgServiceUrl;

    /********************************************************************************/
    /* page */

    @Cacheable(value="10m", key = "'ShowPage:'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionShow(Pageable pageable) {
        return ticketPageRepository.findAllByStatus(0,pageable);
    }

    @Override
    public Page<TicketEntity> QueryTicketPageOptionByCityAndTypeAndTitle(String city, String type, String title, Pageable pageable) {
        return ticketPageRepository.findAllByCityLikeAndTypeLikeAndTitleLikeAndStatus(city,type,title,0,pageable);
    }

    @Cacheable(value = "10m", key = "#title+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByTitle(String title, Pageable pageable){
        title = '%'+title.trim()+'%';
        return ticketPageRepository.findAllByTitleLikeAndStatus(title, 0,pageable);
    }
    @Cacheable(value="10m",key = "#type+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByType(String type, Pageable pageable) {
        return ticketPageRepository.findAllByTypeAndStatus(type,0,pageable);
    }

    @Cacheable(value="10m",key = "#city+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByCity(String city, Pageable pageable) {
        return ticketPageRepository.findAllByCityAndStatus(city, 0,pageable);
    }

    @Cacheable(value="10m", key = "#city+#type+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByCityAndType(String city, String type, Pageable pageable) {
        return ticketPageRepository.findAllByCityAndTypeAndStatus(city, type, 0,pageable);
    }


    @Cacheable(value="10m",key = "#firstDateString + #secondDateString+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByDateRange(String firstDateString, String secondDateString, Pageable pageable) {
        Date firstDate = ChangeStringToDate(firstDateString);
        Date secondDate = ChangeStringToDate(secondDateString);

        return ticketPageRepository.findAllByStartDateBetweenAndStatusOrEndDateBetweenAndStatus(firstDate,secondDate,0,firstDate,secondDate,0,pageable);
    }

    @Cacheable(value="10m",key = "#lowPrice + #highPrice+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByPriceRange(double lowPrice, double highPrice, Pageable pageable) {
        return ticketPageRepository.findAllByLowpriceBetweenAndStatusOrHighpriceBetweenAndStatus(lowPrice,highPrice,0,lowPrice,highPrice,0,pageable);
    }

    @Cacheable(value="10m",key = "#city + #firstDateString + #secondDateString+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByCityAndDateRange(String city, String firstDateString, String secondDateString, Pageable pageable) {
        Date firstDate = ChangeStringToDate(firstDateString);
        Date secondDate = ChangeStringToDate(secondDateString);

        return ticketPageRepository.findAllByCityAndStartDateBetweenAndStatusOrCityAndEndDateBetweenAndStatus(city,firstDate,secondDate,0,city,firstDate,secondDate,0,pageable);
    }

    @Cacheable(value="10m",key = "#city + #lowPrice + #highPrice+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByCityAndPriceRange(String city, double lowPrice, double highPrice, Pageable pageable) {
        return ticketPageRepository.findAllByCityAndLowpriceBetweenAndStatusOrCityAndHighpriceBetweenAndStatus(city,lowPrice,highPrice,0,city,lowPrice,highPrice,0,pageable);
    }

    @Cacheable(value="10m",key = "#city + #lowPrice + #highPrice + #firstDateString + #secondDateString+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByCityAndPriceRangeAndDateRange(String city, double lowPrice, double highPrice, String firstDateString, String secondDateString, Pageable pageable) {
        Date firstDate = ChangeStringToDate(firstDateString);
        Date secondDate = ChangeStringToDate(secondDateString);
        return ticketPageRepository.findAllByCityAndLowpriceBetweenAndStartDateBetweenAndStatusOrCityAndLowpriceBetweenAndEndDateBetweenAndStatusOrCityAndHighpriceBetweenAndStartDateBetweenAndStatusOrCityAndHighpriceBetweenAndEndDateBetweenAndStatus(city,lowPrice,highPrice,firstDate,secondDate,0,city,lowPrice,highPrice,firstDate,secondDate,0,city,lowPrice,highPrice,firstDate,secondDate,0,city,lowPrice,highPrice,firstDate,secondDate,0,pageable);
    }
    /********************************************************************************/
    /** add type **/
    @Cacheable(value="10m",key = "#type + #city+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByTypeAndCity(String type, String city, Pageable pageable) {
        return ticketPageRepository.findAllByTypeAndCityAndStatus(type,city,0,pageable);
    }

    @Cacheable(value="10m",key = "#type + #firstDateString + #secondDateString+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByTypeAndDateRange(String type, String firstDateString, String secondDateString, Pageable pageable) {
        Date firstDate = ChangeStringToDate(firstDateString);
        Date secondDate = ChangeStringToDate(secondDateString);
        return ticketPageRepository.findAllByTypeAndStartDateBetweenAndStatusOrTypeAndEndDateBetweenAndStatus(type,firstDate,secondDate,0,type,firstDate,secondDate,0,pageable);
    }

    @Cacheable(value="10m",key = "#type + #lowPrice + #highPrice+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByTypeAndPriceRange(String type, double lowPrice, double highPrice, Pageable pageable) {
        return ticketPageRepository.findAllByTypeAndLowpriceBetweenAndStatusOrTypeAndHighpriceBetweenAndStatus(type,lowPrice,highPrice,0,type,lowPrice,highPrice,0,pageable);
    }

    @Cacheable(value="10m",key = "#type + #city + #firstDateString + #secondDateString+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByTypeAndCityAndDateRange(String type, String city, String firstDateString, String secondDateString, Pageable pageable) {
        Date firstDate = ChangeStringToDate(firstDateString);
        Date secondDate = ChangeStringToDate(secondDateString);
        return ticketPageRepository.findAllByTypeAndCityAndStartDateBetweenAndStatusOrTypeAndCityAndEndDateBetweenAndStatus(type,city,firstDate,secondDate,0,type,city,firstDate,secondDate,0,pageable);
    }

    @Cacheable(value="10m",key = "#type + #city + #lowPrice + #highPrice+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByTypeAndCityAndPriceRange(String type, String city, double lowPrice, double highPrice, Pageable pageable) {
        return ticketPageRepository.findAllByTypeAndCityAndLowpriceBetweenAndStatusOrTypeAndCityAndHighpriceBetweenAndStatus(type,city,lowPrice,highPrice,0,type,city,lowPrice,highPrice,0,pageable);
    }

    @Cacheable(value="10m",key = "#type + #city + #lowPrice + #highPrice + #firstDateString + #secondDateString+':'+#pageable.getPageNumber()")
    @Override
    public Page<TicketEntity> QueryTicketPageOptionByTypeAndCityAndPriceRangeAndDateRange(String type, String city, double lowPrice, double highPrice, String firstDateString, String secondDateString, Pageable pageable) {
        Date firstDate = ChangeStringToDate(firstDateString);
        Date secondDate = ChangeStringToDate(secondDateString);

        return ticketPageRepository.findAllByTypeAndCityAndLowpriceBetweenAndStartDateBetweenAndStatusOrTypeAndCityAndLowpriceBetweenAndEndDateBetweenAndStatusOrTypeAndCityAndHighpriceBetweenAndStartDateBetweenAndStatusOrTypeAndCityAndHighpriceBetweenAndEndDateBetweenAndStatus(type,city,lowPrice,highPrice,firstDate,secondDate,0,type,city,lowPrice,highPrice,firstDate,secondDate,0,type,city,lowPrice,highPrice,firstDate,secondDate,0,type,city,lowPrice,highPrice,firstDate,secondDate,0,pageable);
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

    /* manager */

    //类型：演唱会 体育赛事等等
    @Override
    @CacheEvict(value = "10m",allEntries = true)
    public TicketEntity add(String type, String startDateString, String endDateString, String time, String city, String venue, String title, MultipartFile image, String intro, Long stock, Double lowprice, Double highprice) {
        TicketEntity ticketToInsert = new TicketEntity();

        ticketToInsert.setDates(parseStringtoDateList(startDateString, endDateString));
        ticketToInsert.setStartDate(ChangeStringToDate(startDateString));
        ticketToInsert.setEndDate(ChangeStringToDate(endDateString));

        Date now = new Date();
        ticketToInsert.setType(type);
        ticketToInsert.setTime(time);
        ticketToInsert.setCity(city);
        ticketToInsert.setVenue(venue);
        ticketToInsert.setTitle(title);
        ticketToInsert.setIntro(intro);
        ticketToInsert.setStock(stock);
        ticketToInsert.setLowprice(lowprice);
        ticketToInsert.setHighprice(highprice);
        ticketToInsert.setImage(saveImage(image));
        if(ChangeStringToDate(endDateString).before(now))
            ticketToInsert.setStatus(1);
        else
            ticketToInsert.setStatus(0);
        return ticketRepository.save(ticketToInsert);
    }

    @Override
    @CacheEvict(value = "10m",allEntries = true)
    public TicketEntity update(Long ticketid, String type, String startDateString, String endDateString, String time, String city, String venue, String title, MultipartFile image, String intro, Long stock, Double lowprice, Double highprice) {
        TicketEntity ticketToUpdate = ticketRepository.findById(ticketid);
        String dateRelateInfo = null;
        if (startDateString!=null && endDateString != null)  {
            ticketToUpdate.setDates(parseStringtoDateList(startDateString,endDateString));
            ticketToUpdate.setStartDate(ChangeStringToDate(startDateString));
            ticketToUpdate.setEndDate(ChangeStringToDate(endDateString));
            if((ChangeStringToDate(endDateString)).before(new Date()))
                ticketToUpdate.setStatus(1);
            else
                ticketToUpdate.setStatus(0);
        }
        if (type != null) ticketToUpdate.setType(type);
        if (time != null) ticketToUpdate.setTime(time);
        if (city != null) ticketToUpdate.setCity(city);
        if (venue != null) ticketToUpdate.setVenue(venue);
        if (title != null) ticketToUpdate.setTitle(title);
        if (intro != null) ticketToUpdate.setIntro(intro);
        if (stock != null) ticketToUpdate.setStock(stock);
        if (lowprice != null) ticketToUpdate.setLowprice(lowprice);
        if (highprice != null) ticketToUpdate.setHighprice(highprice);
        if (image != null) ticketToUpdate.setImage(saveImage(image));

        return ticketRepository.save(ticketToUpdate);
    }

    @Override
    public List<TicketEntity> queryTopSixTicket(Pageable pageable) {
        Page<TicketEntity> page = ticketPageRepository.findAllByStatus(0, pageable);
        return page.getContent();

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

    @Override
    public String saveImage(MultipartFile image){
            if (image == null) {
                return null;
            }

            ImgUtils imgUtils = new ImgUtils();
            InputStream afterHandledImage = imgUtils.scale(image);

            UUID uuid = UUID.randomUUID();
            String id = uuid.toString();
            PictureEntity pictureEntity = new PictureEntity();
            pictureEntity.setUuid(id);
            try{
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                copy(afterHandledImage, output);
                pictureEntity.setBase64(output.toByteArray());
                pictureRepository.save(pictureEntity);
                return imgServiceUrl+uuid;
            }
            catch (Exception e){
                System.out.println("here!");
                return null;
        }
    }

    public String parseStringtoDateList(String startDateString, String endDateString){
        //startDateString, endDateString 的格式都是 2018-07-25
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int count = 0;
        String datesString = "";

        try {
            Date startDate = ChangeStringToDate(startDateString);
            Date endDate = ChangeStringToDate(endDateString);
            Date tmpDate = startDate;
            while(tmpDate.compareTo(endDate)==-1){
                datesString += sdf.format(tmpDate)+" , ";

                Calendar addDate = Calendar.getInstance();
                addDate.setTime(tmpDate); //注意在此处将 addDate 的值改为特定日期
                addDate.add(addDate.DATE, 1); //特定时间的1年后
                tmpDate = addDate.getTime();
                count ++;
                if(count > 3)
                    break;
            }
            datesString += sdf.format(endDate);
        }catch (Exception e){
            System.out.println("崩了？");
        }
        return datesString;
    }

    @Override
    @Transactional
    @CacheEvict(value = "10m",allEntries = true)
    public String delete(String ticketIds) {
        String[] splitIds = ticketIds.trim().replace("[","").replace("]","").split(",");
        for(String eachId : splitIds){
            ticketRepository.deleteById(Long.parseLong(eachId.trim()));
        }
        return "ok";
    }

}
