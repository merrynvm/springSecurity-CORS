package com.example.corsApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/custom")
public class HelloController {
    @GetMapping
    private String hello(){
        return "hello from back-end";
    }
}
