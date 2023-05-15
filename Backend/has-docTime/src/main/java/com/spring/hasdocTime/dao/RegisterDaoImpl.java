package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.RegisterInterface;
import com.spring.hasdocTime.repository.TokenRepository;
import com.spring.hasdocTime.security.customUserClass.UserDetailForToken;
import com.spring.hasdocTime.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
public class RegisterDaoImpl implements RegisterInterface {

    private final UserDaoImpl userDao;
    private final DoctorDaoImpl doctorDao;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
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

        revokeAllUserTokens(createdUser);
        saveUserToken(createdUser, jwtAccessToken);

        return AuthenticationResponse.builder().accessToken(jwtAccessToken).refreshToken(jwtRefreshToken).build();
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

        revokeAllUserTokens(createdUser);
        saveUserToken(createdUser, jwtAccessToken);

        return AuthenticationResponse.builder().accessToken(jwtAccessToken).refreshToken(jwtRefreshToken).build();
    }
}
