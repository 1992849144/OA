package org.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("org.java.dao")
public class OaApplication {

    public static void main(String[] args) {
        SpringApplication.run(OaApplication.class, args);
    }

}
