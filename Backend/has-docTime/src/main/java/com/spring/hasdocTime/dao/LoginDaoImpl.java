package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.AuthenticationResponse;
import com.spring.hasdocTime.entity.LoginDetail;
import com.spring.hasdocTime.interfc.LoginInterface;
import com.spring.hasdocTime.repository.UserRepository;
import com.spring.hasdocTime.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginDaoImpl implements LoginInterface {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse loginRequest(LoginDetail loginDetail) {

//        List<User> allUser = userDao.getAllUser();
//        User responseUser = null;
//        for(User user : allUser){
//            if(user.getEmail().equals(loginDetail.getUsername()) && user.getPassword().equals(loginDetail.getPassword())){
//                responseUser = user;
//                break;
//            }
//        }
        System.out.println("fdjkfgsad");

        authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(
                        loginDetail.getUsername(),
                        loginDetail.getPassword()
                )
        );
        System.out.println("here");
        var user = userRepository.findByEmail(loginDetail.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        System.out.println(jwtToken);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
