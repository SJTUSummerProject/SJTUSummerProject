package com.example.ticketdailyjob;

import com.example.ticketdailyjob.Job.DailyJob;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TicketdailyjobApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketdailyjobApplication.class, args);
    }

    @Bean
    public CommandLineRunner ticketDailyJob(DailyJob dailyJob){
        return (args) -> {
            dailyJob.deleteOutTimeTickets();
        };
    }
}
