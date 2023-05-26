package com.spring.hasdocTime.interfaces;

import com.spring.hasdocTime.entity.Admin;
import com.spring.hasdocTime.entity.AuthenticationResponse;
import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;

public interface RegisterInterface {

    public AuthenticationResponse registerAdmin(Admin admin) throws DoesNotExistException, MissingParameterException;

    public AuthenticationResponse registerUser(User user) throws MissingParameterException, DoesNotExistException;

    public AuthenticationResponse registerDoctor(Doctor doctor) throws MissingParameterException, DoesNotExistException;


}
