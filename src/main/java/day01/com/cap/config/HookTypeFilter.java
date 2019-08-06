package day01.com.cap.config;

import org.springframework.core.io.Resource;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

//自定义过滤组件
public class HookTypeFilter implements TypeFilter {
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {

        //获取当前类注解信息
        //AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //获取当前正在扫描的类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //System.out.println("classMetadata : " + classMetadata);
        //获取当前类资源(类的路径)
        Resource resource = metadataReader.getResource();
        //System.out.println("resource : " + resource);
        String className = classMetadata.getClassName();
        System.out.println("className ----> " + className);
        if(className.contains("Order")){
            return true;
        }

        return false;
    }
}
