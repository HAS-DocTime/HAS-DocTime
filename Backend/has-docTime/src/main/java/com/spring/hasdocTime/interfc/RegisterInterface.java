package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.Admin;
import com.spring.hasdocTime.entity.AuthenticationResponse;
import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;

import javax.print.Doc;

public interface RegisterInterface {

    public AuthenticationResponse registerAdmin(Admin admin);

    public AuthenticationResponse registerUser(User user) throws MissingParameterException;

    public AuthenticationResponse registerDoctor(Doctor doctor) throws MissingParameterException;


}
