package day01.com.cap.config;

import day01.com.cap.data.Car;
import org.springframework.context.annotation.Bean;

public class MainConfigOfLifeCycle {

    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Car car(){
        return new Car();
    }
}
