package me.wangran.springcloud.gateway.zuul;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class EurekaZuulApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaZuulApplication.class)
                .web(WebApplicationType.SERVLET).run(args);
    }
}
