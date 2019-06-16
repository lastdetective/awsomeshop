package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 商品服务的类
 *
 * @author llh
 * @since 2019-06-03
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.leyou.item.mapper")
public class
LyItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyItemApplication.class);
    }
}
