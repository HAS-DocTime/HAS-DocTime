package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.AuthenticationResponse;
import com.spring.hasdocTime.entity.LoginDetail;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.LoginInterface;
import com.spring.hasdocTime.repository.UserRepository;
import com.spring.hasdocTime.security.customUserClass.UserDetailForToken;
import com.spring.hasdocTime.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


/**
 * Implementation of the LoginInterface that provides login functionality.
 */
@Service
@RequiredArgsConstructor
public class LoginDaoImpl implements LoginInterface {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    /**
     * Performs a login request for the given login details.
     *
     * @param loginDetail the login details
     * @return an AuthenticationResponse containing the generated JWT token
     * @throws MissingParameterException if any required parameter is missing
     */
    @Override
    public AuthenticationResponse loginRequest(LoginDetail loginDetail) throws MissingParameterException {

        if(loginDetail.getEmail()==null || loginDetail.getEmail().equals("")){
            throw new MissingParameterException("Email");
        }
        if(loginDetail.getPassword()==null || loginDetail.getPassword().equals("")){
            throw new MissingParameterException("Password");
        }

        // Perform authentication
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                loginDetail.getEmail(),
                loginDetail.getPassword());
        Authentication auth = authenticationManager.authenticate(
                usernamePassword
        );

        // Get user information
        var user = userRepository.findByEmail(loginDetail.getEmail()).orElseThrow();
        UserDetailForToken userDetailForToken;

        // Create user detail for token based on user role
        if(user.getRole().toString().equals("DOCTOR")){
            userDetailForToken = new UserDetailForToken(user.getEmail(), user.getDoctor().getId(), user.getRole());
        }else if(user.getRole().toString().equals("PATIENT")){
            userDetailForToken = new UserDetailForToken(user.getEmail(), user.getId(), user.getRole());
        }else{
            userDetailForToken = new UserDetailForToken(user.getEmail(), user.getAdmin().getId(), user.getRole());
        }

        // Generate JWT token
        var jwtToken = jwtService.generateToken(userDetailForToken);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
