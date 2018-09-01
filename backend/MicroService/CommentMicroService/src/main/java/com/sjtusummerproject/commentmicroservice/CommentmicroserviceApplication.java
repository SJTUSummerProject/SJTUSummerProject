package com.sjtusummerproject.commentmicroservice;

//import com.sjtusummerproject.commentmicroservice.Listener.SaveMongoEventListener;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableRabbit
@EnableTransactionManagement
public class CommentmicroserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CommentmicroserviceApplication.class, args);
	}
}
