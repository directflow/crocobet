package com.crocobet.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping("")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Please, use: /swagger-ui/index.html");
    }
}
