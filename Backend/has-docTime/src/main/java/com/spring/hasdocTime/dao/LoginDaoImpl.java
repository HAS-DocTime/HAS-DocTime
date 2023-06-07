package com.spring.hasdocTime.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.hasdocTime.entity.AuthenticationResponse;
import com.spring.hasdocTime.entity.LoginDetail;
import com.spring.hasdocTime.entity.Token;
import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.LoginInterface;
import com.spring.hasdocTime.repository.TokenRepository;
import com.spring.hasdocTime.repository.UserRepository;
import com.spring.hasdocTime.security.customUserClass.UserDetailForToken;
import com.spring.hasdocTime.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static java.lang.Boolean.TRUE;


/**
 * Implementation of the LoginInterface that provides login functionality.
 */
@Service
@RequiredArgsConstructor
public class LoginDaoImpl implements LoginInterface {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
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
        var jwtAccessToken = jwtService.generateToken(userDetailForToken);
        var jwtRefreshToken = jwtService.generateRefreashToken(userDetailForToken);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtAccessToken);

        return AuthenticationResponse.builder().accessToken(jwtAccessToken).refreshToken(jwtRefreshToken).build();
    }


    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader("Authorization");
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);

        // To Extract Email from JWT token need jwtService class
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null ) {
            var userDetails = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, userDetails)) {

                UserDetailForToken userDetailForToken;
                if (userDetails.getRole().toString().equals("DOCTOR")) {
                    userDetailForToken = new UserDetailForToken(userDetails.getEmail(), userDetails.getDoctor().getId(), userDetails.getRole());
                } else {
                    userDetailForToken = new UserDetailForToken(userDetails.getEmail(), userDetails.getId(), userDetails.getRole());
                }

                var accessToken = this.jwtService.generateToken(userDetailForToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private void saveUserToken(User user, String jwtAccessToken) {
        var token  = Token.builder()
                .user(user)
                .tokenString(jwtAccessToken)
                .isExpired(false)
                .build();

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user){
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if(validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(t -> {
            t.setExpired(TRUE);
        });
    }
}

