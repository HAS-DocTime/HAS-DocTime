package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.AuthenticationResponse;
import com.spring.hasdocTime.entity.LoginDetail;
import com.spring.hasdocTime.entity.OtpRequestBody;
import com.spring.hasdocTime.entity.PasswordUpdateBody;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.LoginInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link LoginInterface} interface that provides the business logic for handling login requests.
 */
@Service
public class LoginServiceImpl implements LoginInterface {

    @Autowired
    @Qualifier("loginDaoImpl")
    private LoginInterface loginDao;

    /**
     * Performs a login request using the provided login details.
     *
     * @param loginDetail The login details.
     * @return The authentication response.
     * @throws MissingParameterException If a required parameter is missing in the login details.
     */
    @Override
    public AuthenticationResponse loginRequest(LoginDetail loginDetail) throws MissingParameterException {
        return loginDao.loginRequest(loginDetail);
    }

    @Override
    public Void sendEmailForForgotPassword(String email) {
        return loginDao.sendEmailForForgotPassword(email);
    }

    @Override
    public Boolean otpVerification(OtpRequestBody otpRequestBody) {
        return loginDao.otpVerification(otpRequestBody);
    }

    @Override
    public Boolean saveNewPassword(PasswordUpdateBody passwordUpdateBody) {
        return loginDao.saveNewPassword(passwordUpdateBody);
    }
}
