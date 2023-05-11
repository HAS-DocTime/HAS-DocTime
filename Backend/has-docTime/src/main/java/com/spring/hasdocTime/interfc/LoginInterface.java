package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.AuthenticationResponse;
import com.spring.hasdocTime.entity.LoginDetail;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface LoginInterface {

    public AuthenticationResponse loginRequest(LoginDetail loginDetail) throws MissingParameterException;

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
