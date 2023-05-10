package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.AuthenticationResponse;
import com.spring.hasdocTime.entity.LoginDetail;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;

public interface LoginInterface {

    public AuthenticationResponse loginRequest(LoginDetail loginDetail) throws MissingParameterException;
}
