package com.nian.cloudEyes;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.nian.cloudEyes.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class CloudEyesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudEyesApplication.class, args);
    }

}
