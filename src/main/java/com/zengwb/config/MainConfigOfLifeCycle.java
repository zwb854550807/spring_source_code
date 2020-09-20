package com.zengwb.config;

import com.zengwb.data.Car;
import org.springframework.context.annotation.Bean;

public class MainConfigOfLifeCycle {

    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Car car(){
        return new Car();
    }
}
