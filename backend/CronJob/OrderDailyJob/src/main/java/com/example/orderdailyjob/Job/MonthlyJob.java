package com.example.orderdailyjob.Job;


import com.example.orderdailyjob.DataModel.Dao.*;
import com.example.orderdailyjob.DataModel.Domain.*;
import org.apache.commons.collections.map.MultiKeyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.*;

@Component
public class MonthlyJob {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MonthlyReportRepository monthlyReportRepository;

    RestTemplate restTemplate =new RestTemplate();
    @Value("${storage}")
    Double storage;
    @Value("${ticket-service-url}")
    String ticketUrl;
    @Transactional
    public void statisticsReportMonthly() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, -1);
        Date firstDayInMonth = calendar.getTime();
        List<OrderEntity> orderEntities = orderRepository.findAllByOrderTimeBetweenAndStatusLike(firstDayInMonth,now,"待发货");

        Map<Long, MonthlyReportEntity> map = new HashMap<>();
        for (OrderEntity orderEntity : orderEntities) {
            Set<ItemEntity> itemEntities = orderEntity.getItems();
            for (ItemEntity itemEntity : itemEntities) {
                Long ticketId = itemEntity.getTicketId();
                if (map.containsKey(ticketId)) {
                    MonthlyReportEntity monthlyReportEntity = map.get(ticketId);
                    //change the dailyEntity
                    completeMonthlyEntity(monthlyReportEntity, itemEntity);
                } else {
                    MonthlyReportEntity monthlyReportEntity = new MonthlyReportEntity();
                    //complete the dailyEntity
                    completeMonthlyEntity(monthlyReportEntity, itemEntity);
                    map.put(ticketId, monthlyReportEntity);
                }
            }
        }
        for (Map.Entry<Long, MonthlyReportEntity> entry : map.entrySet()){
            //System.out.println("here");
            MonthlyReportEntity monthlyReportEntity = entry.getValue();
            Long ticketId = entry.getKey();
            calculateMonthlyEntity(monthlyReportEntity,ticketId);
            monthlyReportRepository.save(monthlyReportEntity);
        }
    }

    private void completeMonthlyEntity(MonthlyReportEntity monthlyReportEntity, ItemEntity itemEntity){
        String priceAndAmount = monthlyReportEntity.getPriceAndAmount();
        if (priceAndAmount == null) priceAndAmount = "";
        StringBuilder sb = new StringBuilder(priceAndAmount);
        if (!priceAndAmount.isEmpty()) sb.append(':');
        sb.append(itemEntity.getPrice());sb.append(' ');sb.append(itemEntity.getNumber());
        monthlyReportEntity.setPriceAndAmount(sb.toString());
    }

    private void calculateMonthlyEntity(MonthlyReportEntity monthlyReportEntity, Long ticketId){
        MultiValueMap<String, Long> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("id",ticketId);
        String url = ticketUrl + ticketId;
        TicketEntity ticketEntity = restTemplate.getForObject(url, TicketEntity.class);
        Map<Double, Long> map = parseJson(monthlyReportEntity.getPriceAndAmount());

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH,-1);

        monthlyReportEntity.setTicketId(ticketEntity.getId());
        monthlyReportEntity.setTitle(ticketEntity.getTitle());
        monthlyReportEntity.setCity(ticketEntity.getCity());
        monthlyReportEntity.setDate(now);
        monthlyReportEntity.setTotalPrice(calculateTotalPrice(map));
        monthlyReportEntity.setRate(calculateRate(map));
        monthlyReportEntity.setPriceAndAmount(calculateAmount(map));
        monthlyReportEntity.setYear(calendar.get(Calendar.YEAR));
        monthlyReportEntity.setMonth(calendar.get(Calendar.MONTH));

        monthlyReportRepository.save(monthlyReportEntity);
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
