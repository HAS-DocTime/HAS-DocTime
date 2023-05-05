package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.AuthenticationResponse;
import com.spring.hasdocTime.entity.LoginDetail;
import com.spring.hasdocTime.interfc.LoginInterface;
import com.spring.hasdocTime.repository.UserRepository;
import com.spring.hasdocTime.security.customUserClass.UserDetailForToken;
import com.spring.hasdocTime.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginDaoImpl implements LoginInterface {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public AuthenticationResponse loginRequest(LoginDetail loginDetail) {

        authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(
                        loginDetail.getEmail(),
                        loginDetail.getPassword()
                )
        );
        var user = userRepository.findByEmail(loginDetail.getEmail()).orElseThrow();
        UserDetailForToken userDetailForToken = new UserDetailForToken(user.getId(), user.getEmail(), user.getRole());
        if(user.getRole().toString().equals("DOCTOR")){
            userDetailForToken = new UserDetailForToken(user.getDoctor().getId(), user.getEmail(), user.getRole());
        }
        var jwtToken = jwtService.generateToken(userDetailForToken);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
