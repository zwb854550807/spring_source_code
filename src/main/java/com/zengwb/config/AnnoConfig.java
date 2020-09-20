package com.zengwb.config;

import com.zengwb.data.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnnoConfig {

    @Bean
    public Person getPerson1(){
        return new Person("lisi",21);
    }
}
