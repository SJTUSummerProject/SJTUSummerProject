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
public class AnnuallyJob {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    AnnuallyReportRepository annuallyReportRepository;

    RestTemplate restTemplate =new RestTemplate();
    @Value("${storage}")
    Double storage;
    @Value("${ticket-service-url}")
    String ticketUrl;
    @Transactional
    public void statisticsReportAnnually() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.YEAR, -1);
        Date firstDayInYear = calendar.getTime();
        List<OrderEntity> orderEntities = orderRepository.findAllByOrderTimeBetweenAndStatusLike(firstDayInYear,now,"待发货");

        Map<Long, AnnuallyReportEntity> map = new HashMap<>();
        for (OrderEntity orderEntity : orderEntities) {
            Set<ItemEntity> itemEntities = orderEntity.getItems();
            for (ItemEntity itemEntity : itemEntities) {
                Long ticketId = itemEntity.getTicketId();
                if (map.containsKey(ticketId)) {
                    AnnuallyReportEntity annuallyReportEntity = map.get(ticketId);
                    //change the dailyEntity
                    completeAnnuallyEntity(annuallyReportEntity,itemEntity);
                } else {
                    AnnuallyReportEntity annuallyReportEntity = new AnnuallyReportEntity();
                    //complete the dailyEntity
                    completeAnnuallyEntity(annuallyReportEntity,itemEntity);
                    map.put(ticketId, annuallyReportEntity);
                }
            }
        }
        for (Map.Entry<Long, AnnuallyReportEntity> entry : map.entrySet()){
            //System.out.println("here");
            AnnuallyReportEntity annuallyReportEntity = entry.getValue();
            Long ticketId = entry.getKey();
            calculateAnnuallyEntity(annuallyReportEntity,ticketId);
            annuallyReportRepository.save(annuallyReportEntity);
        }
    }

    private void completeAnnuallyEntity(AnnuallyReportEntity annuallyReportEntity, ItemEntity itemEntity){
        String priceAndAmount = annuallyReportEntity.getPriceAndAmount();
        if (priceAndAmount == null) priceAndAmount = "";
        StringBuilder sb = new StringBuilder(priceAndAmount);
        if (!priceAndAmount.isEmpty()) sb.append(':');
        sb.append(itemEntity.getPrice());sb.append(' ');sb.append(itemEntity.getNumber());
        annuallyReportEntity.setPriceAndAmount(sb.toString());
    }

    private void calculateAnnuallyEntity(AnnuallyReportEntity annuallyReportEntity, Long ticketId){
        MultiValueMap<String, Long> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("id",ticketId);
        String url = ticketUrl + ticketId;
        TicketEntity ticketEntity = restTemplate.getForObject(url, TicketEntity.class);
        Map<Double, Long> map = parseJson(annuallyReportEntity.getPriceAndAmount());

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.YEAR,-1);

        annuallyReportEntity.setTicketId(ticketEntity.getId());
        annuallyReportEntity.setCity(ticketEntity.getCity());
        annuallyReportEntity.setTitle(ticketEntity.getTitle());
        annuallyReportEntity.setRate(calculateRate(map));
        annuallyReportEntity.setTotalPrice(calculateTotalPrice(map));
        annuallyReportEntity.setPriceAndAmount(calculateAmount(map));
        annuallyReportEntity.setDate(now);
        annuallyReportEntity.setYear(calendar.get(Calendar.YEAR));

        annuallyReportRepository.save(annuallyReportEntity);
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
