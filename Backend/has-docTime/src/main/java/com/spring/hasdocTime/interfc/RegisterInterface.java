package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.security.RegisterResponse;

public interface RegisterInterface {

    RegisterResponse register(User user);

}
