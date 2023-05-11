package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.Admin;
import com.spring.hasdocTime.entity.AuthenticationResponse;
import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.RegisterInterface;
import com.spring.hasdocTime.security.customUserClass.UserDetailForToken;
import com.spring.hasdocTime.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
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
    public AuthenticationResponse registerUser(User user) throws MissingParameterException, DoesNotExistException {
        var createdUser = userDao.createUser(user);

        // Only needed in Login
//        authenticationManager.authenticate
//                (new UsernamePasswordAuthenticationToken(
//                                user.getEmail(),
//                                user.getPassword()
//                        )
//                );

        UserDetailForToken userDetailForToken = new UserDetailForToken(createdUser.getEmail(), createdUser.getId(), createdUser.getRole());
        var jwtAccessToken = jwtService.generateToken(userDetailForToken);
        var jwtRefreshToken = jwtService.generateRefreashToken(userDetailForToken);

        return AuthenticationResponse.builder().accessToken(jwtAccessToken).refreshToken(jwtRefreshToken).build();
    }

    @Override
    public AuthenticationResponse registerDoctor(Doctor doctor) throws MissingParameterException, DoesNotExistException {
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
        UserDetailForToken userDetailForToken = new UserDetailForToken(createdDoctor.getUser().getEmail(), createdDoctor.getId(), createdDoctor.getUser().getRole());
        var jwtAccessToken = jwtService.generateToken(userDetailForToken);
        var jwtRefreshToken = jwtService.generateRefreashToken(userDetailForToken);

        return AuthenticationResponse.builder().accessToken(jwtAccessToken).refreshToken(jwtRefreshToken).build();
    }
}
