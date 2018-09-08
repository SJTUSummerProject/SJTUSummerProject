package com.example.orderdailyjob.job;

import com.example.orderdailyjob.DataModel.Dao.DailyReportRepository;
import com.example.orderdailyjob.DataModel.Domain.DailyReportEntity;
import com.example.orderdailyjob.Job.DailyJob;
import com.example.orderdailyjob.Mock.TicketTemplateMock;
import com.example.orderdailyjob.OrderdailyjobApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.*;

public class DailyJobTest extends OrderdailyjobApplicationTests {
    @Autowired
    DailyReportRepository dailyReportRepository;

    @Test
    public void stasticsReportDaily(){
        dailyJob.statisticsReportDaily();
        assertNotNull( dailyReportRepository.findAll());
    }

    @Test
    public void completeDailyEntity() {
        DailyReportEntity dailyReportEntity = dailyJob.completeDailyEntity(getDailyReportEntity(), getItemEntity());
        assertEquals("100.0 1", dailyReportEntity.getPriceAndAmount());
    }

    @Test
    public void calculateDailyEntity() {
        dailyJob.setRestTemplate(new TicketTemplateMock());
        DailyReportEntity dailyReportEntity = dailyJob.calculateDailyEntity(getDailyCompletedReportEntity(), 1l);
        assertEquals("123", dailyReportEntity.getCity());
    }

    @Test
    public void parseJson() {
        String json = "100.0 1:200.0 1:100.0 1";
        Map<Double, Long> result = dailyJob.parseJson(json);
        assertEquals((Long)2l, (Long)result.get(100.0));
        assertEquals((Long)1l, (Long)result.get(200.0));
    }

    @Test
    public void calculateTotalPrice() {
        Double price = dailyJob.calculateTotalPrice(getMap());
        assertEquals((Double)400.0, (Double)price);
    }

    @Test
    public void calculateRate() {
        Double rage = dailyJob.calculateRate(getMap());
        assertEquals((Double)0.003, (Double)rage, 0.0001);
    }

    @Test
    public void calculateAmount() {
        String json = dailyJob.calculateAmount(getMap());
        assertEquals("100.0 2:200.0 1", json);
    }
}