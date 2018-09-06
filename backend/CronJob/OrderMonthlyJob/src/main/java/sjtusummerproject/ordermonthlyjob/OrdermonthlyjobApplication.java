package sjtusummerproject.ordermonthlyjob;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sjtusummerproject.ordermonthlyjob.DataModel.Job.MonthlyJob;

@SpringBootApplication
public class OrdermonthlyjobApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdermonthlyjobApplication.class, args);
	}

	@Bean
	public CommandLineRunner  orderMonthlyJob(MonthlyJob monthlyJob){
		return (args) -> {
			monthlyJob.statisticsReportMonthly();
		};
	}
}
