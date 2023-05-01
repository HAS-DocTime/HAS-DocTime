package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.interfc.RegisterInterface;
import com.spring.hasdocTime.repository.UserRepository;
import com.spring.hasdocTime.security.jwt.JwtService;
import com.spring.hasdocTime.security.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterDaoImpl implements RegisterInterface {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public RegisterResponse register(User request) {

        var user = User.builder()
                .email(request.getEmail())
                .age(request.getAge())
                .dob(request.getDob())
                .contact(request.getContact())
                .bloodGroup(request.getBloodGroup())
                .height(request.getHeight())
                .weight(request.getWeight())
                .name(request.getName())
                .gender(request.getGender())
                .patientChronicIllness(request.getPatientChronicIllness())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();


        var savedUser = userRepository.save(user);
        int id = userRepository.findByEmail(request.getEmail()).get().getId();

        var jwtToken = jwtService.generateToken(user);
        return RegisterResponse.builder()
                .token(jwtToken)
                .id(id)
                .build();

    }

}
