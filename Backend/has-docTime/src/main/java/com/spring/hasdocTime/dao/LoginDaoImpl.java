package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.interfc.LoginInterface;
import com.spring.hasdocTime.repository.UserRepository;
import com.spring.hasdocTime.security.AuthResponse;
import com.spring.hasdocTime.security.jwt.JwtService;
import com.spring.hasdocTime.security.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginDaoImpl implements LoginInterface {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthResponse loginRequest(RegisterRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();

    }

}
