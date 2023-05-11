package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.AuthenticationResponse;
import com.spring.hasdocTime.entity.LoginDetail;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.LoginInterface;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LoginServiceImpl implements LoginInterface {

    @Autowired
    @Qualifier("loginDaoImpl")
    private LoginInterface loginDao;

    @Override
    public AuthenticationResponse loginRequest(LoginDetail loginDetail) throws MissingParameterException {
        return loginDao.loginRequest(loginDetail);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        loginDao.refreshToken(request, response);
    }
}
