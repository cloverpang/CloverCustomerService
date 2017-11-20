package com.clover.customer.controller;

import io.swagger.annotations.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@RequestMapping("/Info")
public class InfoController {

    @ApiOperation(value="获取Info信息", notes="")
    @RequestMapping(value={"/"}, method= RequestMethod.GET)
    public String index(){
        return "Hello Customer Service";
    }
}
