package sjtusummerproject.orderannuallyjob;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sjtusummerproject.orderannuallyjob.Job.AnnuallyJob;

@SpringBootApplication
public class OrderannuallyjobApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderannuallyjobApplication.class, args);
	}

	@Bean
	public CommandLineRunner orderAnnuallyJob(AnnuallyJob annuallyJob){
		return (args)->{
			annuallyJob.statisticsReportAnnually();
		};
	}
}
