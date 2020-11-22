package com.atguigu.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;

@SpringBootApplication
public class MainRedis3000 {
    public static void main(String[] args) {
        SpringApplication.run(MainRedis3000.class,args);
    }
}
