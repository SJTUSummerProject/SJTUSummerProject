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
    String url;
    @Transactional
    public void statisticsReportDaily() {
        Date now = new Date();
        List<OrderEntity> orderEntities = orderRepository.findAllByOrOrderTimeEqualsAndStatusLike(now, "待发货");
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
                }
            }
        }
        for (Map.Entry<Long, DailyReportEntity> entry : map.entrySet()){
            DailyReportEntity dailyReportEntity = entry.getValue();
            Long ticketId = entry.getKey();
            caculateDailyEntity(dailyReportEntity, ticketId);
            dailyReportRepository.save(dailyReportEntity);
        }
    }

    private void completeDailyEntity(DailyReportEntity dailyReportEntity, ItemEntity itemEntity){
        String priceAndAmount = dailyReportEntity.getPriceAndAmount();
        StringBuilder sb = new StringBuilder(priceAndAmount);
        if (!priceAndAmount.isEmpty()) sb.append(':');
        sb.append(itemEntity.getPrice());sb.append(' ');sb.append(itemEntity.getNumber());
        dailyReportEntity.setPriceAndAmount(sb.toString());
    }

    private void caculateDailyEntity(DailyReportEntity dailyReportEntity, Long ticketId){
        MultiValueMap<String, Long> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("id",ticketId);
        url+=ticketId;
        TicketEntity ticketEntity = restTemplate.getForObject(url, TicketEntity.class);
        Map<Double, Long> map = parseJson(dailyReportEntity.getPriceAndAmount());
        dailyReportEntity.setTicketId(ticketId);
        dailyReportEntity.setCity(ticketEntity.getCity());
        dailyReportEntity.setTitle(ticketEntity.getTitle());
        dailyReportEntity.setDate(new Date());
        dailyReportEntity.setTotalPrice(caculateTotalPrice(map));
        dailyReportEntity.setRate(caculateRate(map));
        dailyReportRepository.save(dailyReportEntity);
    }

    private Map<Double, Long> parseJson(String json){
        Map<Double, Long> result = new HashMap<>();
        String kv[] = json.split(":");
        for (String s : kv){
            String map[] = s.split(":") ;
            result.put(Double.parseDouble(map[0]), Long.parseLong(map[1]));
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
}