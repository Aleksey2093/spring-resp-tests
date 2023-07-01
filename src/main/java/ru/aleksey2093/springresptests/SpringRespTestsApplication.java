package ru.aleksey2093.springresptests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringRespTestsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRespTestsApplication.class, args);
    }

}
