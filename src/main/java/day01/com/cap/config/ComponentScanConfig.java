package day01.com.cap.config;

import day01.com.cap.data.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(value = "day01.com.cap", includeFilters = { @Filter(type = FilterType.CUSTOM,
        classes = HookTypeFilter.class)}, useDefaultFilters = false)
public class ComponentScanConfig {
    @Bean
    public Person getPerson(){
        return new Person("wangwu", 25);
    }

}
