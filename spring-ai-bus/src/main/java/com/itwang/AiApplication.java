package com.itwang;


import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@MapperScans({
        @MapperScan(basePackages = {"com.itwang.dao.mapper"})
})
@EnableTransactionManagement
@SpringBootApplication
public class AiApplication {


    public static void main(String[] args) {
        SpringApplication.run(AiApplication.class, args);
    }
}
