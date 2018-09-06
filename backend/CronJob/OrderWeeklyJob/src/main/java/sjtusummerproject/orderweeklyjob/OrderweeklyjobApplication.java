package sjtusummerproject.orderweeklyjob;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sjtusummerproject.orderweeklyjob.Job.WeeklyJob;

@SpringBootApplication
public class OrderweeklyjobApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderweeklyjobApplication.class, args);
	}

	@Bean
	public CommandLineRunner orderWeeklyJob(WeeklyJob weeklyJob){
		return (args) -> {
			weeklyJob.statisticsReportWeekly();
		};
	}
}
