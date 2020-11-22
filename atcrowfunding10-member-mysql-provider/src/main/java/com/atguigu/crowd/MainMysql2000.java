package com.atguigu.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@MapperScan("com.atguigu.crowd.mapper")
@SpringBootApplication
public class MainMysql2000 {
    public static void main(String[] args) {
        SpringApplication.run(MainMysql2000.class,args);
    }
}
