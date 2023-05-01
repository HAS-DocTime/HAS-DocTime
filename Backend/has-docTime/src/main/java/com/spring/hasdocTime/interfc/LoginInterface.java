package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.security.AuthResponse;
import com.spring.hasdocTime.security.RegisterRequest;

public interface LoginInterface {

    AuthResponse loginRequest(RegisterRequest request);

}
