package me.wangran.springcloud.eureka.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class EurekaProviderController {

    @RequestMapping("/hello")
    public String hello(
            @RequestParam String consumer) {
        return "This is Provider, hello " + consumer;
    }
}
