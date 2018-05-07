package me.wangran.springcloud.eureka.controller;

import me.wangran.springcloud.eureka.component.EurekaConsumerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class EurekaConsumerController {

    @Autowired
    private EurekaConsumerClient client;

    @RequestMapping("/hello/{consumer}")
    public String hello(
            @PathVariable(name = "consumer") String consumer) {
        return client.hello(consumer);
    }
}
