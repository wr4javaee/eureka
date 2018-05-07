package me.wangran.springcloud.eureka;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class EurekaConsumerApplication {

    public static void main(String[] args) {
       new SpringApplicationBuilder(EurekaConsumerApplication.class)
                .web(WebApplicationType.SERVLET).run(args);
    }
}