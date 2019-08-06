package day01.com.cap.config;


import day01.com.cap.data.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

public class LazyConfig {

    @Bean
    @Lazy
    public Person getPerson(){
        System.out.println("给容器中加载Person....");
        return new Person();
    }
}
