package com.example.orderdailyjob.Job;

import com.example.orderdailyjob.DataModel.Dao.DailyReportRepository;
import com.example.orderdailyjob.DataModel.Dao.OrderRepository;
import com.example.orderdailyjob.DataModel.Domain.DailyReportEntity;
import com.example.orderdailyjob.DataModel.Domain.ItemEntity;
import com.example.orderdailyjob.DataModel.Domain.OrderEntity;
import com.example.orderdailyjob.DataModel.Domain.TicketEntity;
import org.apache.commons.collections.map.MultiKeyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class DailyJob {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    DailyReportRepository dailyReportRepository;

    RestTemplate restTemplate =new RestTemplate();
    @Value("${storage}")
    Double storage;
    @Value("${ticket-service-url}")
    String ticketUrl;
    @Transactional
    public void statisticsReportDaily() {
        Date now = new Date();
        List<OrderEntity> orderEntities = orderRepository.findAllByOrderTimeEqualsAndStatusLike(now, "待发货");
        //System.out.println(orderEntities.get(0).getId());
        Map<Long, DailyReportEntity> map = new HashMap<>();
        for (OrderEntity orderEntity : orderEntities) {
            Set<ItemEntity> itemEntities = orderEntity.getItems();
            for (ItemEntity itemEntity : itemEntities) {
                Long ticketId = itemEntity.getTicketId();
                if (map.containsKey(ticketId)) {
                    DailyReportEntity dailyReportEntity = map.get(ticketId);
                    //change the dailyEntity
                    completeDailyEntity(dailyReportEntity, itemEntity);
                } else {
                    DailyReportEntity dailyReportEntity = new DailyReportEntity();
                    //complete the dailyEntity
                    completeDailyEntity(dailyReportEntity, itemEntity);
                    map.put(ticketId, dailyReportEntity);
                }
            }
        }
        for (Map.Entry<Long, DailyReportEntity> entry : map.entrySet()){
            //System.out.println("here");
            DailyReportEntity dailyReportEntity = entry.getValue();
            Long ticketId = entry.getKey();
            caculateDailyEntity(dailyReportEntity, ticketId);
            dailyReportRepository.save(dailyReportEntity);
        }
    }

    private void completeDailyEntity(DailyReportEntity dailyReportEntity, ItemEntity itemEntity){
        String priceAndAmount = dailyReportEntity.getPriceAndAmount();
        if (priceAndAmount == null) priceAndAmount = "";
        StringBuilder sb = new StringBuilder(priceAndAmount);
        if (!priceAndAmount.isEmpty()) sb.append(':');
        sb.append(itemEntity.getPrice());sb.append(' ');sb.append(itemEntity.getNumber());
        dailyReportEntity.setPriceAndAmount(sb.toString());
    }

    private void caculateDailyEntity(DailyReportEntity dailyReportEntity, Long ticketId){
        MultiValueMap<String, Long> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("id",ticketId);
        String url = ticketUrl + ticketId;
        TicketEntity ticketEntity = restTemplate.getForObject(url, TicketEntity.class);
        Map<Double, Long> map = parseJson(dailyReportEntity.getPriceAndAmount());
        dailyReportEntity.setTicketId(ticketEntity.getId());
        dailyReportEntity.setCity(ticketEntity.getCity());
        dailyReportEntity.setTitle(ticketEntity.getTitle());
        dailyReportEntity.setDate(new Date());
        dailyReportEntity.setTotalPrice(caculateTotalPrice(map));
        dailyReportEntity.setRate(caculateRate(map));
        dailyReportEntity.setPriceAndAmount(caculateAmount(map));
        dailyReportRepository.save(dailyReportEntity);
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

    private Double caculateTotalPrice(Map<Double, Long> map){
        Double total = 0.0;
        for (Map.Entry<Double, Long> entry : map.entrySet()){
            total += (entry.getKey()*entry.getValue());
        }
        return total;
    }

    private Double caculateRate(Map<Double, Long> map){
        double total = 0.0;
        for (Long value : map.values()){
            total += value;
        }
        return total/storage;
    }

    private String caculateAmount(Map<Double, Long> map){
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