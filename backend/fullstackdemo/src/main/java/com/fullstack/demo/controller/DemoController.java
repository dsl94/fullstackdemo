package com.fullstack.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class DemoController {

    @GetMapping("/demo")
    public ResponseEntity getDemo() {
        return ResponseEntity.ok("Nemanja");
    }
}
