package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.LoginDetail;
import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.interfc.LoginInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
@CrossOrigin(value = "http://localhost:4200")
public class LoginController {

    @Autowired
    @Qualifier("loginServiceImpl")
    private LoginInterface loginService;

    private String username;
    private String password;


    @PostMapping("")
    public ResponseEntity<User> loginRequest(@RequestBody LoginDetail loginDetail){
        System.out.println(loginDetail.getEmail());
        System.out.println(loginDetail.getPassword());
        User responseUser = loginService.loginRequest(loginDetail);
        if(responseUser == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            return new ResponseEntity(responseUser, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(responseUser, HttpStatus.OK);
    }

}
