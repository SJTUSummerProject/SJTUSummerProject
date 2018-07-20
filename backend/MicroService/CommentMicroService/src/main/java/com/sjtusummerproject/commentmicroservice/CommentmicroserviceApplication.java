package com.sjtusummerproject.commentmicroservice;

//import com.sjtusummerproject.commentmicroservice.Listener.SaveMongoEventListener;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableRabbit
public class CommentmicroserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CommentmicroserviceApplication.class, args);
	}
}
