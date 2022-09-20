package az.et.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StartPoint {

    public static void main(String[] args) {
        SpringApplication.run(StartPoint.class, args);
    }

}
