package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.interfc.RegisterInterface;
import com.spring.hasdocTime.security.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterInterface{

    @Autowired
    @Qualifier("registerDaoImpl")
    private RegisterInterface registerDao;

    @Override
    public RegisterResponse register(User user) {
        return registerDao.register(user);
    }
}


