package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.Admin;
import com.spring.hasdocTime.entity.AuthenticationResponse;
import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.RegisterInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterInterface{

    @Autowired
    @Qualifier("registerDaoImpl")
    private RegisterInterface registerDao;

    @Override
    public AuthenticationResponse registerAdmin(Admin admin) {
        return registerDao.registerAdmin(admin);
    }

    @Override
    public AuthenticationResponse registerUser(User user) throws MissingParameterException, DoesNotExistException {
        return registerDao.registerUser(user);
    }

    @Override
    public AuthenticationResponse registerDoctor(Doctor doctor) throws MissingParameterException, DoesNotExistException{
        return registerDao.registerDoctor(doctor);
    }
}
