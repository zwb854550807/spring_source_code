package com.zengwb.config;

import com.zengwb.data.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(value = "day01.com.zengwb", includeFilters = { @Filter(type = FilterType.CUSTOM,
        classes = HookTypeFilter.class)}, useDefaultFilters = false)
public class ComponentScanConfig {
    @Bean
    public Person getPerson(){
        return new Person("wangwu", 25);
    }

}
