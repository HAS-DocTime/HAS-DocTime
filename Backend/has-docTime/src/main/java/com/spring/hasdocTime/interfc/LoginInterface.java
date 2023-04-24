package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.LoginDetail;
import com.spring.hasdocTime.entity.User;

public interface LoginInterface {

    public User loginRequest(LoginDetail loginDetail);
}
