package com.zengwb.config;

import com.zengwb.data.Person;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ScopeConfig {

    private static Log log = LogFactory.getLog(ScopeConfig.class);

    @Bean("person")
    @Scope("prototype")
    public Person getPerson(){
        System.out.println("容器开始加载person类,开始构造person对象...");
        Person p = new Person();
        p.setName("james");
        p.setAge(19);
        return p;
    }

}
