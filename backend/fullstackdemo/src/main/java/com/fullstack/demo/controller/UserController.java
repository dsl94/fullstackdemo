package com.fullstack.demo.controller;

import com.fullstack.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity getUsers() {
        return ResponseEntity.ok(userRepository.findAll()) ;
    }
}
