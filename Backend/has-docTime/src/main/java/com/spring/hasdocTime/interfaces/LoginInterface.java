/**
 * Interface defining operations for user login.
 */
package com.spring.hasdocTime.interfaces;

import com.spring.hasdocTime.entity.AuthenticationResponse;
import com.spring.hasdocTime.entity.LoginDetail;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;

public interface LoginInterface {

    /**
     * Performs a login request with the provided login details.
     *
     * @param loginDetail the LoginDetail object containing the user's login credentials
     * @return the AuthenticationResponse object representing the result of the login request
     * @throws MissingParameterException if there are missing parameters in the login details
     */
    public AuthenticationResponse loginRequest(LoginDetail loginDetail) throws MissingParameterException;
}
