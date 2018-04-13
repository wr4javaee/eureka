package me.wangran.springcloud.eureka.component.hystrix;

import me.wangran.springcloud.eureka.component.EurekaConsumerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class EurekaConsumerClientFallback implements EurekaConsumerClient {

    @Override
    public String hello(@RequestParam(value = "consumer") String consumer) {
        return "consumer " + consumer + " fall back message ";
    }
}
