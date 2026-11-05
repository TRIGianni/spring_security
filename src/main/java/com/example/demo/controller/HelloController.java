package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    record HelloRequest(String username){}
    @PostMapping("/private/hello")
    public String privateHello(@RequestBody HelloRequest request){
        return "Hello "+request.username;
    }

    @GetMapping("/private/hello")
    public String apiHello(){
        return "🔐 Hello World 🔐";
    }

}
