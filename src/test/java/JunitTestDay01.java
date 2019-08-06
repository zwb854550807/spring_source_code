import day01.com.cap.config.AnnoConfig;
import day01.com.cap.config.ComponentScanConfig;
import day01.com.cap.config.LazyConfig;
import day01.com.cap.config.ScopeConfig;
import day01.com.cap.data.Person;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class JunitTestDay01 {

    //spring走mybeans.xml配置文件加载类
    @Test
    public void testXml() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("mybeans.xml");
        Person person = (Person) applicationContext.getBean("beanPerson");
        person.setAge(29);
        System.out.println(person.toString());
    }

    //spring通过@Configuration和@Bean注解加载类文件
    @Test
    public void testAnno() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AnnoConfig.class);
        String[] beanNamesForType = annotationConfigApplicationContext.getBeanNamesForType(Person.class);
        for (String beanName : beanNamesForType) {
            System.out.println("beanName = " + beanName);
        }
        Person person = (Person) annotationConfigApplicationContext.getBean("getPerson1");
        System.out.println(person.toString());
    }

    //@ComponentScan注解,扫描指定路径下的文件
    @Test
    public void testComponentScan() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ComponentScanConfig.class);
        String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            System.out.println("beanName = " + beanName);
        }
    }

    @Test
    public void testScopePrototype() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ScopeConfig.class);
        System.out.println("IOC容器创建完成...");
        String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            System.out.println("scope beanName = " + beanName);
        }

        Object person1 = annotationConfigApplicationContext.getBean("person");
        Object person2 = annotationConfigApplicationContext.getBean("person");
        System.out.println("person1 = " + person1 + "-----------person2" + person2);
        System.out.println("equals:" + (person1 == person2));

    }

    @Test
    public void testLazy() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(LazyConfig.class);
        System.out.println("容器创建完成....");
        Object person = annotationConfigApplicationContext.getBean("getPerson");
        System.out.println("person = " + person);
    }


    @Test
    public void test(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -2);
        String twoMonthAgo = sdf.format(cal.getTime());
        System.out.println(twoMonthAgo);

    }
}
