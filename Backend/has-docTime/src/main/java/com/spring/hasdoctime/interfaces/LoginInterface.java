/**
 * Interface defining operations for user login.
 */
package com.spring.hasdoctime.interfaces;

import com.spring.hasdoctime.entity.*;
import com.spring.hasdoctime.exceptionhandling.exception.MissingParameterException;
import jakarta.mail.MessagingException;

public interface LoginInterface {

    /**
     * Performs a login request with the provided login details.
     *
     * @param loginDetail the LoginDetail object containing the user's login credentials
     * @return the AuthenticationResponse object representing the result of the login request
     * @throws MissingParameterException if there are missing parameters in the login details
     */
    public AuthenticationResponse loginRequest(LoginDetail loginDetail) throws MissingParameterException;

    Boolean sendEmailForForgotPassword(SendOtpEmail sendOtpEmail) throws MessagingException;
    Boolean otpVerification(OtpRequestBody otpRequestBody);

    Boolean saveNewPassword(PasswordUpdateBody passwordUpdateBody);
}
