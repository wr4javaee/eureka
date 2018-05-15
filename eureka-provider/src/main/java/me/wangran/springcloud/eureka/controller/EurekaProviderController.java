package me.wangran.springcloud.eureka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class EurekaProviderController {

    private static final Logger LOG = LoggerFactory.getLogger(EurekaProviderController.class);

    @RequestMapping("/hello")
    public String hello(
            @RequestParam String consumer) {
        LOG.info("This is Provider, hello {}", consumer);
        try {
            Thread.sleep(4731);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "This is Provider, hello " + consumer;
    }
}
