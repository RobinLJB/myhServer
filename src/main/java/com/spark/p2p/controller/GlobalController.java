package com.spark.p2p.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class GlobalController {
	
	@ExceptionHandler({Exception.class})   
    public String exception(Exception e) {   
        System.out.println(e.getMessage());   
        e.printStackTrace();   
        return "exception";   
    }
}
