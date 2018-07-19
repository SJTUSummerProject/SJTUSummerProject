package com.sjtusummerproject.commentmicroservice;

import com.sjtusummerproject.commentmicroservice.Listener.SaveMongoEventListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication

public class CommentmicroserviceApplication {
	@Bean
	SaveMongoEventListener saveMongoEventListener(){
		return new SaveMongoEventListener();
	}

	public static void main(String[] args) {
		SpringApplication.run(CommentmicroserviceApplication.class, args);
	}
}
