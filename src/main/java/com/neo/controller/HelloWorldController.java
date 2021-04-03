package com.neo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class HelloWorldController {



    protected Logger logger =  LoggerFactory.getLogger(this.getClass());
	
    @RequestMapping("/hello")
    public String hello() {
        logger.info("you access path is Hello World");
        return "you access path is Hello World";
    }

    @RequestMapping("/")
    public String root() {
        logger.info("you access path is root");
        return "you access path is root";
    }
	
    @RequestMapping("/v1")
    public String v1() {
        logger.info("you access path is v1");
        return "you access path is v1";
    }
	
    @RequestMapping("/v2")
    public String v2() {
        logger.info("you access path is v2");
        return "you access path is v2";
    }
	
    @RequestMapping("/api/v1")
    public String api1() {
        logger.info("you access path is /api/v1");
        return "you access path is /api/v1";
    }

    @RequestMapping("/api/v2")
    public String api2() {
        logger.info("you access path is /api/v2");
        return "you access path is /api/v2";
    }

}
