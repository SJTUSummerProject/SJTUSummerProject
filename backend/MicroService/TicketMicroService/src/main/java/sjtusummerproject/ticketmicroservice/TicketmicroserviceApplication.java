package sjtusummerproject.ticketmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TicketmicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketmicroserviceApplication.class, args);
	}
}
