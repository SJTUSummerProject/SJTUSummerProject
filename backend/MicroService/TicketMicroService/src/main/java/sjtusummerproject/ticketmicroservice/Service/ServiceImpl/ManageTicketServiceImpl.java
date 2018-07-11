package sjtusummerproject.ticketmicroservice.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sjtusummerproject.ticketmicroservice.DataModel.Dao.TicketRepository;
import sjtusummerproject.ticketmicroservice.DataModel.Domain.TicketEntity;
import sjtusummerproject.ticketmicroservice.Service.ManageTicketService;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class ManageTicketServiceImpl implements ManageTicketService {
    @Autowired
    TicketRepository ticketRepository;

    @Override
    public List<TicketEntity> QueryTicketOptionByExactDate(String date) {
        List<TicketEntity> list = ticketRepository.findAllByDate(date);
        return list;
    }

    @Override
    public List<TicketEntity> QueryTicketOptionByDateRange(String firstDate, String secondDate) {
        HashMap<String,Integer> SmallToCompare = SplitDateString(firstDate);

        HashMap<String,Integer> LargeToCompare = SplitDateString(secondDate);

        List<TicketEntity> allEntity = ticketRepository.findAll();
        List<TicketEntity> filterlist = new LinkedList<>();
        for(TicketEntity EachEntity : allEntity){
            String EachEntityDate = EachEntity.getDate();
            if(EachEntityDate.contains("至")){
                String EachEntityDate1 = EachEntityDate.split("至")[0].trim();
                String EachEntityDate2 = EachEntityDate.split("至")[1].trim();
                HashMap<String,Integer> StartDateMap = SplitDateString(EachEntityDate1);
                HashMap<String,Integer> EndDateMap = SplitDateString(EachEntityDate2);
                if((CompareDate(SmallToCompare,StartDateMap)==true)&&(CompareDate(StartDateMap,LargeToCompare)==true))
                    filterlist.add(EachEntity);
                else if((CompareDate(SmallToCompare,EndDateMap)==true)&&(CompareDate(EndDateMap,LargeToCompare)==true))
                    filterlist.add(EachEntity);
                else
                    continue;
            }
            else{
                HashMap<String,Integer> ExactDateMap = SplitDateString(EachEntityDate);
                if((CompareDate(SmallToCompare,ExactDateMap)==true)&&(CompareDate(ExactDateMap,LargeToCompare)==true))
                    filterlist.add(EachEntity);
                else
                    continue;
            }
        }
        return filterlist;
    }

    @Override
    public List<TicketEntity> QueryTicketOptionByCity(String city) {
        List<TicketEntity> res = ticketRepository.findAllByCity(city);
        return res;
    }

    @Override
    public List<TicketEntity> QueryTicketOptionByType(String type) {
        List<TicketEntity> res = ticketRepository.findAllByType(type);
        return res;
    }

    @Override
    public List<TicketEntity> QueryTicketOptionByPriceRange(double firstPrice, double secondPrice) {
        List<TicketEntity> allEntity = ticketRepository.findAll();
        List<TicketEntity> filterEntity = new LinkedList<>();

        for(TicketEntity EachEntity : allEntity){
            if(ComparePrice(firstPrice,secondPrice,EachEntity)==true)
                filterEntity.add(EachEntity);
        }
        return filterEntity;
    }

    @Override
    public TicketEntity QueryTicketOptionById(Long id) {
        TicketEntity ticketEntity = ticketRepository.findById(id);
        return ticketEntity;
    }

    @Override
    public String AddTicketsOptionByJson(String ticketsInfo, String type) {
        return null;
    }

    @Override
    public String AddTicketOptionByString(String ticketsInfo, String type) {
        return null;
    }

    @Override
    public String DeleteTicketOptionByExactDate(String date) { return null; }

    @Override
    public String DeleteTicketOptionByDateRange(String firstDate, String secondDate) {
        return null;
    }

    @Override
    public String DeleteTicketOptionByCity(String city) {
        return null;
    }

    @Override
    public String DeleteTicketOptionByType(String type) {
        return null;
    }

    @Override
    public String DeleteTicketOptionByPriceRange(String firstPrice, String secondPrice) {
        return null;
    }

    @Override
    public String DeleteTicketOptionByPriceRange(int firstPrice, int secondPrice) {
        return null;
    }

    @Override
    public String DeleteTicketOptionByPriceRange(Long firstPrice, Long secondPrice) {
        return null;
    }

    @Override
    public String UpdateTicketEntireOptionByIdFromEntity(Long Id, TicketEntity ticketEntity) {
        return null;
    }

    @Override
    public String UpdateTicketTypeOptionByIdFromEntity(Long Id, TicketEntity ticketEntity) {
        return null;
    }

    @Override
    public String UpdateTicketTypeOptionByIdFromType(Long Id, String type) {
        return null;
    }

    @Override
    public String UpdateTicketDateOptionByIdFromEntity(Long Id, TicketEntity ticketEntity) {
        return null;
    }

    @Override
    public String UpdateTicketDateOptionByIdFromType(Long Id, String date) {
        return null;
    }

    @Override
    public String UpdateTicketCityOptionByIdFromEntity(Long Id, TicketEntity ticketEntity) {
        return null;
    }

    @Override
    public String UpdateTicketCityOptionByIdFromType(Long Id, String city) {
        return null;
    }

    @Override
    public String UpdateTicketVenueOptionByIdFromEntity(Long Id, TicketEntity ticketEntity) {
        return null;
    }

    @Override
    public String UpdateTicketVenueOptionByIdFromType(Long Id, String venue) {
        return null;
    }

    @Override
    public String UpdateTicketTitleOptionByIdFromEntity(Long Id, TicketEntity ticketEntity) {
        return null;
    }

    @Override
    public String UpdateTicketTitleOptionByIdFromTitle(Long Id, String title) {
        return null;
    }

    @Override
    public String UpdateTicketImageOptionByIdFromEntity(Long Id, TicketEntity ticketEntity) {
        return null;
    }

    @Override
    public String UpdateTicketImageOptionByIdFromTitle(Long Id, String image) {
        return null;
    }

    @Override
    public String UpdateTicketIntroOptionByIdFromEntity(Long Id, TicketEntity ticketEntity) {
        return null;
    }

    @Override
    public String UpdateTicketIntroOptionByIdFromTitle(Long Id, String intro) {
        return null;
    }

    @Override
    public String UpdateTicketStockOptionByIdFromEntity(Long Id, TicketEntity ticketEntity) {
        return null;
    }

    @Override
    public String UpdateTicketStockOptionByIdFromTitle(Long Id, Long stock) {
        return null;
    }

    @Override
    public String UpdateTicketLowPriceOptionByIdFromEntity(Long Id, TicketEntity ticketEntity) {
        return null;
    }

    @Override
    public String UpdateTicketLowPriceOptionByIdFromTitle(Long Id, int lowprice) {
        return null;
    }

    @Override
    public String UpdateTicketHighPriceOptionByIdFromEntity(Long Id, TicketEntity ticketEntity) {
        return null;
    }

    @Override
    public String UpdateTicketHighPriceOptionByIdFromTitle(Long Id, int highprice) {
        return null;
    }

    public HashMap<String,Integer> SplitDateString(String date){
        HashMap<String,Integer> res = new HashMap<>();

        String Rest = date.split("年")[1];
        int year = Integer.parseInt(date.split("年")[0]);
        int month = Integer.parseInt(Rest.split("月")[0]);
        Rest = Rest.split("月")[1];
        int day = Integer.parseInt(Rest.split("日")[0]);

        res.put("year",year);
        res.put("month",month);
        res.put("day",day);
        return res;
    }

    /*
    * if date1 <= date2 return true
    * else return false
    * */
    public boolean CompareDate(HashMap<String,Integer> DateMap1, HashMap<String,Integer> DateMap2){
        int firstyear = DateMap1.get("year");
        int firstmonth = DateMap1.get("month");
        int firstday = DateMap1.get("day");

        int secondyear = DateMap2.get("year");
        int secondmonth = DateMap2.get("month");
        int secondday = DateMap2.get("day");

        if(secondyear>firstyear)
            return true;
        else if(secondyear<firstyear)
            return false;
        /* 2 date year is same */
        else{
            if(secondmonth>firstmonth)
                return true;
            else if(secondmonth<firstmonth)
                return false;
            /* 2 date month is same */
            else{
                if(secondday>=firstday)
                    return true;
                else
                    return false;
            }
        }
    }

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
