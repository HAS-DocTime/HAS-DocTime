package com.spring.hasdocTime.service;

import com.spring.hasdocTime.interfc.LoginInterface;
import com.spring.hasdocTime.security.AuthResponse;
import com.spring.hasdocTime.security.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginInterface {

    @Autowired
    @Qualifier("loginDaoImpl")
    private LoginInterface loginDao;

    @Override
    public AuthResponse loginRequest(RegisterRequest request) {
        return loginDao.loginRequest(request);
    }
}
