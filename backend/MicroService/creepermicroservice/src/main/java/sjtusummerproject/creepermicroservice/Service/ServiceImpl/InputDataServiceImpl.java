
package sjtusummerproject.creepermicroservice.Service.ServiceImpl;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Service;
import sjtusummerproject.creepermicroservice.DataModel.Domain.TicketEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class InputDataServiceImpl{
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    static long id = 0l;

    public String inputdata(List<String> list){
    	try {
            traversedir(list);
            return "ok";
        }
        catch (Exception e){
    	   return "faliure";
        }
    }

    public String traversedir(List<String> citylist) {
        //如果dir不以文件分隔符结尾，自动添加文件分隔符
        try{
            //得到"数据"文件夹
            File dataDirFile = org.springframework.util.ResourceUtils.getFile("classpath:数据"+File.separator);
            //得到"数据"文件夹下的所有文件夹
            File[] underDataDirFiles = dataDirFile.listFiles();
            for(int i = 0; i<underDataDirFiles.length;i++){
                String type = ParseType(underDataDirFiles[i].getName());//获得 票品类型
                //得到"有" 文件夹
                File underTypeDirFile = underDataDirFiles[i].listFiles()[0];
                //File underTypeDirFile=org.springframework.util.ResourceUtils.getFile("classpath:有"+File.separator);
                //得到不同城市的具体数据文件
                File[] underYouDirFiles = underTypeDirFile.listFiles();
                System.out.print(1);
                for(int j = 0; j < underYouDirFiles.length;j++) {
                    String absolutePath = underYouDirFiles[j].getAbsolutePath();
                    String city = null;
                    for (String eachCity : citylist) {
                        if (absolutePath.contains(eachCity))
                            city = eachCity;
                    }
                    readdirfile(underYouDirFiles[j].getAbsolutePath(), city, type);
                }
            }
            return "ok";
        }
        catch (Exception e){
            return "failure";
        }
    }

    public String readdirfile(String filename,String cityName,String type){
        File datafile = new File(filename);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(datafile));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
//                System.out.println(tempString);
                parsejson(tempString,cityName,type);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    public String parsejson(String string,String cityName,String type){
        String sql = "INSERT INTO ticket(id,city,dates,end_date,highprice,image,intro,lowprice,start_date,stock,time,title,type,venue)" +
                " VALUES(:id,:city,:dates,:endDate,:highprice,:image,:intro,:lowprice,:startDate,:stock,:time,:title,:type,:venue)"
                ;
        JSONObject jsonObject= JSONObject.fromObject(string);
//        System.out.println(jsonObject);
        Map<String,String> tmp = new HashMap<>();
        for(Object k : jsonObject.keySet()){
            Object v = jsonObject.get(k);
            tmp.put(k.toString(),v.toString());
        }
        List<TicketEntity> ticketEntities = new ArrayList<>();
        for(String k : tmp.keySet()){
//            System.out.println(tmp.get(k));
            JSONObject detailObject = JSONObject.fromObject(tmp.get(k));
            /* 每一个票 */
            Map<String,String> tmp1 = new HashMap<>();
            for(Object l : detailObject.keySet()) {
                Object s = detailObject.get(l);
                tmp1.put(l.toString(),s.toString());
            }
            for(String l : tmp1.keySet()){
                String s = tmp1.get(l);
//                System.out.println(s);
//                System.out.println(cityName);
                if(l.equals("date"))
                    s = ParseDate(s);
                if(l.equals("venue"))
                    s = ParseVenue(s);
            }

            /*插入演唱会*/
            String EachType = type;
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
            ticketEntity.setId(++id);

            ticketEntities.add(ticketEntity);
//            ticketRepository.save(ticketEntity);
//            System.out.println("highprice "+EachHighPrice);
//            System.out.println("lowprice "+EachLowPrice);

        }
        SqlParameterSource[] sqlParameterSource = SqlParameterSourceUtils.createBatch(ticketEntities.toArray());
        namedParameterJdbcTemplate.batchUpdate(sql,sqlParameterSource);
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
//        System.out.println(result[1]);
        return result[1];
    }

    public String ParseVenue(String venue){
        String[] result = venue.split("\\[");
//        System.out.println(result[0]);
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

    public String ParseType(String filename){
        String type ;
        filename = filename.trim();
        System.out.println(filename);
        if(filename.contains("儿童亲子"))
            type = "parenting";
        else if(filename.contains("歌剧话剧"))
            type = "opera";
        else if(filename.contains("曲艺杂技"))
            type = "acrobatics";
        else if(filename.contains("体育赛事"))
            type = "sports";
        else if(filename.contains("舞蹈芭蕾"))
            type = "dancing";
        else if(filename.contains("演唱会"))
            type = "vocal concert";
        else    //音乐会
            type = "concert";
        return type;
    }
}
