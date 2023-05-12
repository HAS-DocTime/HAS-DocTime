package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.AuthenticationResponse;
import com.spring.hasdocTime.entity.LoginDetail;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
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
    public AuthenticationResponse loginRequest(LoginDetail loginDetail) throws MissingParameterException {

        if(loginDetail.getEmail()==null || loginDetail.getEmail().equals("")){
            throw new MissingParameterException("Email");
        }
        if(loginDetail.getPassword()==null || loginDetail.getPassword().equals("")){
            throw new MissingParameterException("Password");
        }

        authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(
                        loginDetail.getEmail(),
                        loginDetail.getPassword()
                )
        );
        var user = userRepository.findByEmail(loginDetail.getEmail()).orElseThrow();
        UserDetailForToken userDetailForToken;
        if(user.getRole().toString().equals("DOCTOR")){
            userDetailForToken = new UserDetailForToken(user.getEmail(), user.getDoctor().getId(), user.getRole());
        }else if(user.getRole().toString().equals("PATIENT")){
            userDetailForToken = new UserDetailForToken(user.getEmail(), user.getId(), user.getRole());
        }else{
            userDetailForToken = new UserDetailForToken(user.getEmail(), user.getAdmin().getId(), user.getRole());
        }
        var jwtToken = jwtService.generateToken(userDetailForToken);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
