package sjtusummerproject.codemicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
public class CodemicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodemicroserviceApplication.class, args);
	}
}
