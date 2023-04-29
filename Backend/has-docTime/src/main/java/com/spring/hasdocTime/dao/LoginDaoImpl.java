package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.AuthenticationResponse;
import com.spring.hasdocTime.entity.LoginDetail;
import com.spring.hasdocTime.interfc.LoginInterface;
import com.spring.hasdocTime.repository.UserRepository;
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

    //private LoginDetail user1 = new LoginDetail("trupti@luv2code.com", "123456");

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
                        loginDetail.getEmail(),
                        loginDetail.getPassword()
                )
        );
        System.out.println("here");
        var user = userRepository.findByEmail(loginDetail.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user.getUsername());
        System.out.println(jwtToken);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
