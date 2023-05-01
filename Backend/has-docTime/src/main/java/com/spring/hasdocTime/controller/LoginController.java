package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.interfc.LoginInterface;
import com.spring.hasdocTime.security.AuthResponse;
import com.spring.hasdocTime.security.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
@CrossOrigin(value = "*")
public class LoginController {

    private final LoginInterface loginService;

    @Autowired
    public LoginController(@Qualifier("loginServiceImpl") LoginInterface loginService) {
        this.loginService = loginService;
    }

    @PostMapping("")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(loginService.loginRequest(request));
    }

}
