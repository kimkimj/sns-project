package com.example.finalproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {

    @GetMapping()
    public String hello(){
        return "김미지";
    }

    @GetMapping("/cicd")
    public String cicdCheck() { return "cicd again";}

}

