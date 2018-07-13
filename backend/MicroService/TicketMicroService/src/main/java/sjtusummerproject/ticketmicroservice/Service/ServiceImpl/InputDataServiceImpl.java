package sjtusummerproject.ticketmicroservice.Service.ServiceImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sjtusummerproject.ticketmicroservice.DataModel.Dao.TicketRepository;
import sjtusummerproject.ticketmicroservice.DataModel.Domain.TicketEntity;
import sjtusummerproject.ticketmicroservice.Service.InputDataService;

import javax.json.JsonObject;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class InputDataServiceImpl implements InputDataService{
    @Autowired
    TicketRepository ticketRepository;

    public List<TicketEntity> getfilterlist(String filterString){
        System.out.println(filterString);
        List<TicketEntity> ticketEntities = new LinkedList<>();
        JSONArray filterJsonObject = JSONArray.fromObject(filterString);

        for(Object object : filterJsonObject){
            System.out.println(object);
            ticketEntities.add(parseentityfromstring(object.toString()));
        }
        return ticketEntities;
    }

    public TicketEntity parseentityfromstring(String string){
//        JSONObject jsonObject = JSONObject.fromObject(string);
//        TicketEntity ticketEntity = new TicketEntity();
//        for(Object k : jsonObject.keySet()){
//            if(k.toString().equals("id"))
//                ticketEntity.setId(Long.parseLong(jsonObject.get(k).toString()));
//            else if(k.toString().equals("type"))
//                ticketEntity.setType(jsonObject.get(k).toString());
//            else if(k.toString().equals("date"))
//                ticketEntity.setDate(jsonObject.get(k).toString());
//            else if(k.toString().equals("city"))
//                ticketEntity.setCity(jsonObject.get(k).toString());
//            else if(k.toString().equals("venue"))
//                ticketEntity.setVenue(jsonObject.get(k).toString());
//            else if(k.toString().equals("title"))
//                ticketEntity.setTitle(jsonObject.get(k).toString());
//            else if(k.toString().equals("image"))
//                ticketEntity.setImage(jsonObject.get(k).toString());
//            else if(k.toString().equals("intro"))
//                ticketEntity.setIntro(jsonObject.get(k).toString());
//            else if(k.toString().equals("stock"))
//                ticketEntity.setStock(Long.parseLong(jsonObject.get(k).toString()));
//            else if(k.toString().equals("lowprice"))
//                ticketEntity.setLowprice(Double.valueOf(jsonObject.get(k).toString()));
//            else
//                ticketEntity.setHighprice(Double.valueOf(jsonObject.get(k).toString()));
//
//        }
//        return ticketEntity;
        return null;
    }
    public String inputdata(List<String> list){
        traversedir(list);
        return "ok";
    }

    public String traversedir(List<String> citylist){
        String dirName = "/Users/sky/Desktop/软件工程导论/SJTUSummerProject/SJTUSummerProject/Python/webspider/xsq2/演唱会信息/有";
        //如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dirName.endsWith(File.separator)) {
            dirName = dirName + File.separator;
        }
        File dirFile = new File(dirName);
        //如果dir对应的文件不存在，或者不是一个文件夹则退出
        if (!dirFile.exists() || (!dirFile.isDirectory())) {
            System.out.println("List失败！找不到目录：" + dirName);
        }

        //列出文件夹下所有的文件
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                System.out.println(files[i].getAbsolutePath() + " 是文件！");
                String absolutePath = files[i].getAbsolutePath();
                String city = null;
                for(String eachCity : citylist){
                    if(absolutePath.contains(eachCity))
                        city = eachCity;
                }
                readdirfile(files[i].getAbsolutePath(),city);
            } else if (files[i].isDirectory()) {
                System.out.println(files[i].getAbsolutePath() + " 是目录！");
            }
        }
        return "ok";
    }

    public String readdirfile(String filename,String cityName){
        File datafile = new File(filename);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(datafile));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                System.out.println(tempString);
                parsejson(tempString,cityName);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    public String parsejson(String string,String cityName){
        JSONObject jsonObject= JSONObject.fromObject(string);
        System.out.println(jsonObject);
        Map<String,String> tmp = new HashMap<>();
        for(Object k : jsonObject.keySet()){
            Object v = jsonObject.get(k);
            tmp.put(k.toString(),v.toString());
        }
        for(String k : tmp.keySet()){
            System.out.println(tmp.get(k));
            JSONObject detailObject = JSONObject.fromObject(tmp.get(k));
            /* 每一个票 */
            Map<String,String> tmp1 = new HashMap<>();
            for(Object l : detailObject.keySet()) {
                Object s = detailObject.get(l);
                tmp1.put(l.toString(),s.toString());
            }
            for(String l : tmp1.keySet()){
                String s = tmp1.get(l);
                System.out.println(s);
                System.out.println(cityName);
                if(l.equals("date"))
                    s = ParseDate(s);
                if(l.equals("venue"))
                    s = ParseVenue(s);
            }

            /*插入演唱会*/
            Long Eachid = 0L;
            String EachType = "vocal concert";
            String EachDate = ParseDate(tmp1.get("date"));
            String EachCity = cityName;
            String EachVenue = ParseVenue(tmp1.get("venue"));
            String EachTitle = tmp1.get("title");
            String EachImage = tmp1.get("image");
            String EachIntro = tmp1.get("briefintro");
            Long EachStock = 1000L;
            Double EachLowPrice = ParseLowPrice(tmp1.get("price"));
            Double EachHighPrice = ParseHighPrice(tmp1.get("price"));

            HashMap<String,Object> tmpDatesMap = ParseStringtoDateList(EachDate);

            TicketEntity ticketEntity = new TicketEntity();
            ticketEntity.setType(EachType);
            /*dates*/
            ticketEntity.setDates((String)tmpDatesMap.get("Dates"));
            /*startdate*/
            ticketEntity.setStartDate((Date)tmpDatesMap.get("startDate"));
            /*enddate*/
            ticketEntity.setEndDate((Date)tmpDatesMap.get("endDate"));
            /*time*/
            System.out.println("the city "+cityName);
            ticketEntity.setTime("20:00");
            ticketEntity.setCity(EachCity);
            ticketEntity.setVenue(EachVenue);
            ticketEntity.setTitle(EachTitle);
            ticketEntity.setImage(EachImage);
            ticketEntity.setIntro(EachIntro);
            ticketEntity.setStock(EachStock);
            ticketEntity.setLowprice(EachLowPrice);
            ticketEntity.setHighprice(EachHighPrice);

            ticketRepository.save(ticketEntity);
            System.out.println("highprice "+EachHighPrice);
            System.out.println("lowprice "+EachLowPrice);

        }
        return "ok";
    }

    public HashMap<String,Object> ParseStringtoDateList(String Date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String,Object> res = new HashMap<>();
        int count = 0;

        String startDateString = null;
        String endDateString = null;
        if(Date.contains("至")){
            startDateString = Date.split("至")[0];
            endDateString = Date.split("至")[1];
        }
        else{
            startDateString = Date;
            endDateString = Date;
        }

        Date startDate = new Date();
        Date endDate = new Date();

        startDateString = startDateString.replace("年","-").replace("月","-").replace("日"," ").trim();
        endDateString = endDateString.replace("年","-").replace("月","-").replace("日"," ").trim();
        try {
            startDate = sdf.parse(startDateString);
            endDate = sdf.parse(endDateString);
            res.put("startDate",startDate);
            res.put("endDate",endDate);

            String datesString = "";
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
            res.put("Dates",datesString);
        }catch (Exception e){
            System.out.println("崩了？");
        }

        return res;
    }

    public String ParseDate(String date){
        String[] result = date.split("：");
        System.out.println(result[1]);
        return result[1];
    }

    public String ParseVenue(String venue){
        String[] result = venue.split("\\[");
        System.out.println(result[0]);
        return result[0];
    }

    public double ParseLowPrice(String price){
        if(price.contains("(")){
            price = price.split("\\(")[0];
        }
        if(price.contains("~")){
            String[] prices = price.split("~");
            String lowprice = prices[0].replace('￥',' ').trim();
            return Double.valueOf(lowprice);
        }
        else{
            String lowprice = price.replace('￥',' ').trim();
            return Double.valueOf(lowprice);
        }
    }

    public double ParseHighPrice(String price){
        if(price.contains("(")){
            price = price.split("\\(")[0];
        }
        if(price.contains("~")){
            String[] prices = price.split("~");
            String lowprice = prices[1].replace('￥',' ').trim();
            return Double.valueOf(lowprice);
        }
        else{
            String lowprice = price.replace('￥',' ').trim();
            return Double.valueOf(lowprice);
        }
    }
}
