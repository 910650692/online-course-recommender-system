package com.osrs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.osrs.CodeGeneration;
@SpringBootApplication
@MapperScan("com.osrs.dao")
public class OsrsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OsrsApplication.class, args);


    }

}
