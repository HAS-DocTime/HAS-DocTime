package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.Admin;
import com.spring.hasdocTime.entity.AuthenticationResponse;
import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.interfc.RegisterInterface;
import com.spring.hasdocTime.repository.UserRepository;
import com.spring.hasdocTime.security.customUserClass.UserDetailForToken;
import com.spring.hasdocTime.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterDaoImpl implements RegisterInterface {

    private final UserDaoImpl userDao;
    private final DoctorDaoImpl doctorDao;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthenticationResponse registerAdmin(Admin admin) {
        return null;
    }

    @Override
    public AuthenticationResponse registerUser(User user) {
//        System.out.println("dao");
        var createdUser = userDao.createUser(user);

        // Only needed in Login
//        authenticationManager.authenticate
//                (new UsernamePasswordAuthenticationToken(
//                                user.getEmail(),
//                                user.getPassword()
//                        )
//                );

        UserDetailForToken userDetailForToken = new UserDetailForToken(createdUser.getId(), createdUser.getEmail(), createdUser.getRole());
        var jwtToken = jwtService.generateToken(userDetailForToken);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse registerDoctor(Doctor doctor) {
        System.out.println(doctor);
        var createdUser = userDao.createUser(doctor.getUser());
        doctor.setUser(createdUser);
        var createdDoctor = doctorDao.createDoctor(doctor);

        // Only needed in Login
//        authenticationManager.authenticate
//                (new UsernamePasswordAuthenticationToken(
//                                doctor.getUser().getEmail(),
//                                doctor.getUser().getPassword()
//                        )
//                );
        UserDetailForToken userDetailForToken = new UserDetailForToken(createdDoctor.getId(), createdDoctor.getUser().getEmail(), createdDoctor.getUser().getRole());
        var jwtToken = jwtService.generateToken(userDetailForToken);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
