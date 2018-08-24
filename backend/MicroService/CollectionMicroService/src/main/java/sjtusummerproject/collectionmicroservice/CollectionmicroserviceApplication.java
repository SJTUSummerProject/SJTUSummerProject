package sjtusummerproject.collectionmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CollectionmicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollectionmicroserviceApplication.class, args);
    }
}
