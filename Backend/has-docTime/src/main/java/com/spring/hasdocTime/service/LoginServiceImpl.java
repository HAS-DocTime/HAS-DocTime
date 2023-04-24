package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.LoginDetail;
import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.interfc.AdminInterface;
import com.spring.hasdocTime.interfc.LoginInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginInterface {

    @Autowired
    @Qualifier("loginDaoImpl")
    private LoginInterface loginDao;

    @Override
    public User loginRequest(LoginDetail loginDetail) {
        return loginDao.loginRequest(loginDetail);
    }
}
