import day01.com.cap.config.MainConfigOfLifeCycle;
import day01.com.cap.data.Car;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCLifeCycleTest {

    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
        System.out.println("容器创建完毕...");
//        Car car =(Car) applicationContext.getBean("car");
//        car.init();
        applicationContext.close();
    }
}
