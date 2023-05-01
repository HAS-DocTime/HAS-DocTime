package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.interfc.RegisterInterface;
import com.spring.hasdocTime.security.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("register")
@CrossOrigin(value = "*")
public class RegisterController {

    private final RegisterInterface registerService;

    @Autowired
    public RegisterController(@Qualifier("registerServiceImpl") RegisterInterface registerService) {
        this.registerService = registerService;
    }
    @PostMapping("/user")
    public ResponseEntity<RegisterResponse> register(
            @RequestBody User request
    ){
        return ResponseEntity.ok(registerService.register(request));
    }

}
