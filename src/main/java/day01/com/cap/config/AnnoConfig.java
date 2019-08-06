package day01.com.cap.config;

import day01.com.cap.data.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnnoConfig {

    @Bean
    public Person getPerson1(){
        return new Person("lisi",21);
    }
}
