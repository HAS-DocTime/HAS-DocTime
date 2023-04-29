package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.AuthenticationResponse;
import com.spring.hasdocTime.entity.LoginDetail;
import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.interfc.LoginInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    @Qualifier("loginServiceImpl")
    private LoginInterface loginService;

    private String username;
    private String password;
    @PostMapping("/")
    public ResponseEntity<AuthenticationResponse> loginRequest(@RequestBody LoginDetail loginDetail){
        return ResponseEntity.ok(loginService.loginRequest(loginDetail));
    }

}
