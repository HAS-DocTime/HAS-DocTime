package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.AuthenticationResponse;
import com.spring.hasdocTime.entity.LoginDetail;

public interface LoginInterface {

    public AuthenticationResponse loginRequest(LoginDetail loginDetail);
}
