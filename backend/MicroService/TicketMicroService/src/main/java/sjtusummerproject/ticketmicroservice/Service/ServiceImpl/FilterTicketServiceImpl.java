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

    @Override
    public List<TicketEntity> FilterTicketOptionByExactDate(List<TicketEntity> list, String date) {
        List<TicketEntity> res = new LinkedList<>();
        HashMap<String,Integer> OriginDateMap = SplitDateString(date);
        for(TicketEntity EachEntity : list){
            String EachEntityDate = EachEntity.getDate();
            if(EachEntityDate.contains("至")){
                String EachEntityDate1 = EachEntityDate.split("至")[0].trim();
                String EachEntityDate2 = EachEntityDate.split("至")[1].trim();
                HashMap<String,Integer> StartDateMap = SplitDateString(EachEntityDate1);
                HashMap<String,Integer> EndDateMap = SplitDateString(EachEntityDate2);
                if((CompareDate(StartDateMap,OriginDateMap)==true)&&(CompareDate(OriginDateMap,EndDateMap)==true))
                    res.add(EachEntity);
                else
                    continue;
            }
            else{
                if(EachEntityDate.equals(date))
                    res.add(EachEntity);
            }
        }
        return res;
    }

    @Override
    public List<TicketEntity> FilterTicketOptionByDateRange(List<TicketEntity> list, String firstdate, String seconddate) {
        HashMap<String,Integer> DateMap1 = SplitDateString(firstdate);
        HashMap<String,Integer> DateMap2 = SplitDateString(seconddate);
        List<TicketEntity> res = new LinkedList<>();
        for(TicketEntity EachEntity : list){
            String EachEntityDate = EachEntity.getDate();
            if(EachEntityDate.contains("至")){
                String EachEntityDate1 = EachEntityDate.split("至")[0].trim();
                String EachEntityDate2 = EachEntityDate.split("至")[1].trim();
                HashMap<String,Integer> StartDateMap = SplitDateString(EachEntityDate1);
                HashMap<String,Integer> EndDateMap = SplitDateString(EachEntityDate2);
                if((CompareDate(DateMap1,StartDateMap)==true)&&(CompareDate(StartDateMap,DateMap2)==true))
                    res.add(EachEntity);
                else if((CompareDate(DateMap1,EndDateMap)==true)&&(CompareDate(EndDateMap,DateMap2)==true))
                    res.add(EachEntity);
                else
                    continue;
            }
            else{
                HashMap<String,Integer> ExactDateMap = SplitDateString(EachEntityDate);
                if((CompareDate(DateMap1,ExactDateMap)==true)&&(CompareDate(ExactDateMap,DateMap2)==true))
                    res.add(EachEntity);
                else
                    continue;
            }
        }

        return res;
    }

    @Override
    public List<TicketEntity> FilterTicketOptionByPriceRange(List<TicketEntity> list, double lowprice, double highprice) {
        List<TicketEntity> filterEntity = new LinkedList<>();

        for(TicketEntity EachEntity : list){
            if(ComparePrice(lowprice,highprice,EachEntity)==true)
                filterEntity.add(EachEntity);
        }

        return filterEntity;
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
