package com.example.orderdailyjob;

import com.example.orderdailyjob.DataModel.Domain.DailyReportEntity;
import com.example.orderdailyjob.DataModel.Domain.ItemEntity;
import com.example.orderdailyjob.DataModel.Domain.OrderEntity;
import com.example.orderdailyjob.DataModel.Domain.TicketEntity;
import com.example.orderdailyjob.Job.DailyJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = {"classpath:application-test.properties"})
@SpringBootTest
public class OrderdailyjobApplicationTests {

    @Autowired
    public DailyJob dailyJob;

    public ItemEntity getItemEntity(){
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setNumber(1l);
        itemEntity.setPrice(100);
        return itemEntity;
    }

    public DailyReportEntity getDailyReportEntity(){
        DailyReportEntity dailyReportEntity = new DailyReportEntity();
        dailyReportEntity.setPriceAndAmount("");
        return dailyReportEntity;
    }

    public DailyReportEntity getDailyCompletedReportEntity(){
        DailyReportEntity dailyReportEntity = new DailyReportEntity();
        dailyReportEntity.setPriceAndAmount("100.0 1");
        return dailyReportEntity;
    }

    public Map<Double, Long> getMap(){
        Map<Double, Long> map = new HashMap<>();
        map.put(100.0, 2l);
        map.put(200.0, 1l);
        return map;
    }

    @Test
    public void contextloads() {
    }

}
