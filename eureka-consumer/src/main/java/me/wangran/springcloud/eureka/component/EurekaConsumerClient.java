package me.wangran.springcloud.eureka.component;

import me.wangran.springcloud.eureka.component.hystrix.EurekaConsumerClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "credit-gateway-server",
        fallback = EurekaConsumerClientFallback.class)
public interface EurekaConsumerClient {

    @RequestMapping(value = "/test/hello")
    String hello(@RequestParam(value = "consumer") String consumer);
}