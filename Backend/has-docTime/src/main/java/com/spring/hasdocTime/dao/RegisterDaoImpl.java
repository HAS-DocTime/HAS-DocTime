package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.RegisterInterface;
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
    private final AdminDaoImpl adminDao;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers an admin.
     *
     * @param admin the admin to register
     * @return an authentication response containing the generated JWT token
     * @throws DoesNotExistException     if the user or admin does not exist
     * @throws MissingParameterException if a required parameter is missing
     */
    @Override
    public AuthenticationResponse registerAdmin(Admin admin) throws DoesNotExistException, MissingParameterException {
        var createdUser = userDao.createUser(admin.getUser());
        admin.setUser(createdUser);
        var createdAdmin = adminDao.createAdmin(admin);

        UserDetailForToken userDetailForToken = new UserDetailForToken(createdAdmin.getUser().getEmail(), createdAdmin.getId(), createdAdmin.getUser().getRole());
        var jwtAccessToken = jwtService.generateToken(userDetailForToken);
        var jwtRefreshToken = jwtService.generateRefreashToken(userDetailForToken);

        revokeAllUserTokens(createdUser);
        saveUserToken(createdUser, jwtAccessToken);

        return AuthenticationResponse.builder().accessToken(jwtAccessToken).refreshToken(jwtRefreshToken).build();
    }

    /**
     * Registers a user.
     *
     * @param user the user to register
     * @return an authentication response containing the generated JWT token
     * @throws MissingParameterException if a required parameter is missing
     * @throws DoesNotExistException     if the user does not exist
     */
    @Override
    public AuthenticationResponse registerUser(User user) throws MissingParameterException, DoesNotExistException {
        var createdUser = userDao.createUser(user);

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

    /**
     * Registers a doctor.
     *
     * @param doctor the doctor to register
     * @return an authentication response containing the generated JWT token
     * @throws MissingParameterException if a required parameter is missing
     * @throws DoesNotExistException     if the doctor or user does not exist
     */
    @Override
    public AuthenticationResponse registerDoctor(Doctor doctor) throws MissingParameterException, DoesNotExistException {
        var createdUser = userDao.createUser(doctor.getUser());
        doctor.setUser(createdUser);
        var createdDoctor = doctorDao.createDoctor(doctor);

        UserDetailForToken userDetailForToken = new UserDetailForToken(createdDoctor.getUser().getEmail(), createdDoctor.getId(), createdDoctor.getUser().getRole());
        var jwtAccessToken = jwtService.generateToken(userDetailForToken);
        var jwtRefreshToken = jwtService.generateRefreashToken(userDetailForToken);

        revokeAllUserTokens(createdUser);
        saveUserToken(createdUser, jwtAccessToken);

        return AuthenticationResponse.builder().accessToken(jwtAccessToken).refreshToken(jwtRefreshToken).build();
    }
}
