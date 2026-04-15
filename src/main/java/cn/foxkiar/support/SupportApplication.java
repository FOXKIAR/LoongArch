package cn.foxkiar.support;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.foxkiar.support.mapper")
public class SupportApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupportApplication.class, args);
    }

}
