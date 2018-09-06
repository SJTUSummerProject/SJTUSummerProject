package sjtusummerproject.orderweeklyjob.Job;


import org.apache.commons.collections.map.MultiKeyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sjtusummerproject.orderweeklyjob.DataModel.Dao.OrderRepository;
import sjtusummerproject.orderweeklyjob.DataModel.Dao.WeeklyReportRepository;
import sjtusummerproject.orderweeklyjob.DataModel.Domain.ItemEntity;
import sjtusummerproject.orderweeklyjob.DataModel.Domain.OrderEntity;
import sjtusummerproject.orderweeklyjob.DataModel.Domain.TicketEntity;
import sjtusummerproject.orderweeklyjob.DataModel.Domain.WeeklyReportEntity;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.*;

@Component
public class WeeklyJob {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    WeeklyReportRepository weeklyReportRepository;

    RestTemplate restTemplate =new RestTemplate();
    @Value("${storage}")
    Double storage;
    @Value("${ticket-service-url}")
    String ticketUrl;
    @Transactional
    public void statisticsReportWeekly() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DATE, -6);
        Date mondayThisWeek = calendar.getTime();
        List<OrderEntity> orderEntities = orderRepository.findAllByOrderTimeBetweenAndStatusLike(mondayThisWeek,now,"待发货");

        Map<Long, WeeklyReportEntity> map = new HashMap<>();
        for (OrderEntity orderEntity : orderEntities) {
            Set<ItemEntity> itemEntities = orderEntity.getItems();
            for (ItemEntity itemEntity : itemEntities) {
                Long ticketId = itemEntity.getTicketId();
                if (map.containsKey(ticketId)) {
                    WeeklyReportEntity weeklyReportEntity = map.get(ticketId);
                    //change the dailyEntity
                    completeWeeklyEntity(weeklyReportEntity, itemEntity);
                } else {
                    WeeklyReportEntity weeklyReportEntity = new WeeklyReportEntity();
                    //complete the dailyEntity
                    completeWeeklyEntity(weeklyReportEntity, itemEntity);
                    map.put(ticketId, weeklyReportEntity);
                }
            }
        }
        for (Map.Entry<Long, WeeklyReportEntity> entry : map.entrySet()){
            //System.out.println("here");
            WeeklyReportEntity weeklyReportEntity = entry.getValue();
            Long ticketId = entry.getKey();
            calculateWeeklyEntity(weeklyReportEntity,ticketId);
            weeklyReportRepository.save(weeklyReportEntity);
        }
    }

    private void completeWeeklyEntity(WeeklyReportEntity weeklyReportEntity, ItemEntity itemEntity){
        String priceAndAmount = weeklyReportEntity.getPriceAndAmount();
        if (priceAndAmount == null) priceAndAmount = "";
        StringBuilder sb = new StringBuilder(priceAndAmount);
        if (!priceAndAmount.isEmpty()) sb.append(':');
        sb.append(itemEntity.getPrice());sb.append(' ');sb.append(itemEntity.getNumber());
        weeklyReportEntity.setPriceAndAmount(sb.toString());
    }

    private void calculateWeeklyEntity(WeeklyReportEntity weeklyReportEntity, Long ticketId){
        MultiValueMap<String, Long> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("id",ticketId);
        String url = ticketUrl + ticketId;
        TicketEntity ticketEntity = restTemplate.getForObject(url, TicketEntity.class);
        Map<Double, Long> map = parseJson(weeklyReportEntity.getPriceAndAmount());

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        weeklyReportEntity.setTicketId(ticketEntity.getId());
        weeklyReportEntity.setTitle(ticketEntity.getTitle());
        weeklyReportEntity.setCity(ticketEntity.getCity());
        weeklyReportEntity.setDate(now);
        weeklyReportEntity.setTotalPrice(calculateTotalPrice(map));
        weeklyReportEntity.setRate(calculateRate(map));
        weeklyReportEntity.setPriceAndAmount(calculateAmount(map));
        weeklyReportEntity.setYear(calendar.get(Calendar.YEAR));
        weeklyReportEntity.setMonth(calendar.get(Calendar.MONTH));
        weeklyReportEntity.setWeek(calendar.get(Calendar.WEEK_OF_MONTH));

        weeklyReportRepository.save(weeklyReportEntity);
    }

    private Map<Double, Long> parseJson(String json){
        Map<Double, Long> result = new HashMap<>();
        String kv[] = json.split(":");
        for (String s : kv){
            String map[] = s.split(" ") ;
            Double key = Double.parseDouble(map[0]);
            Long value = Long.parseLong(map[1]);
            if (result.containsKey(key)){
                Long oldValue = result.get(key);
                result.put(key, oldValue + value);
            }
            else result.put(key, value);
        }
        return result;
    }

    private Double calculateTotalPrice(Map<Double, Long> map){
        Double total = 0.0;
        for (Map.Entry<Double, Long> entry : map.entrySet()){
            total += (entry.getKey()*entry.getValue());
        }
        return total;
    }

    private Double calculateRate(Map<Double, Long> map){
        double total = 0.0;
        for (Long value : map.values()){
            total += value;
        }
        return total/storage;
    }

    private String calculateAmount(Map<Double, Long> map){
        StringBuilder sb = new StringBuilder();
        boolean flag =true;
        for (Map.Entry<Double, Long> entry : map.entrySet()){
            if(flag == true){
                sb.append(entry.getKey());
                sb.append(' ');
                sb.append(entry.getValue());
                flag =  false;
            }
            else {
                sb.append(':');
                sb.append(entry.getKey());
                sb.append(' ');
                sb.append(entry.getValue());
            }
        }
        return sb.toString();
    }
}
