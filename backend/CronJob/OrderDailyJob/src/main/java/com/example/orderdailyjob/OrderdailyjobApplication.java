package com.example.orderdailyjob;

import com.example.orderdailyjob.Job.DailyJob;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrderdailyjobApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderdailyjobApplication.class, args);
    }


    @Bean
    public CommandLineRunner ticketDailyJob(DailyJob dailyJob){
        return (args) -> {
            dailyJob.statisticsReportDaily();
        };
    }
}
