package com.spring.hasdoctime.dao;

import com.spring.hasdoctime.entity.Admin;
import com.spring.hasdoctime.entity.AuthenticationResponse;
import com.spring.hasdoctime.entity.Doctor;
import com.spring.hasdoctime.entity.User;
import com.spring.hasdoctime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdoctime.interfaces.RegisterInterface;
import com.spring.hasdoctime.security.customuserclass.UserDetailForToken;
import com.spring.hasdoctime.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implementation of the {@link RegisterInterface} for registering users, admins, and doctors.
 */
@Service
@RequiredArgsConstructor
public class RegisterDaoImpl implements RegisterInterface {

    private final UserDaoImpl userDao;
    private final DoctorDaoImpl doctorDao;
    private final AdminDaoImpl adminDao;
    private final JwtService jwtService;

    /**
     * Registers an admin.
     *
     * @param admin the admin to register
     * @return an authentication response containing the generated JWT token
     * @throws DoesNotExistException     if the user or admin does not exist
     * @throws MissingParameterException if a required parameter is missing
     */
    @Transactional
    @Override
    public AuthenticationResponse registerAdmin(Admin admin) throws DoesNotExistException, MissingParameterException {
        var createdUser = userDao.createUser(admin.getUser());
        admin.setUser(createdUser);
        var createdAdmin = adminDao.createAdmin(admin);

        UserDetailForToken userDetailForToken = new UserDetailForToken(createdAdmin.getUser().getEmail(), createdAdmin.getId(), createdAdmin.getUser().getRole());
        var jwtToken = jwtService.generateToken(userDetailForToken);

        return AuthenticationResponse.builder().token(jwtToken).build();
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
    @Transactional
    public AuthenticationResponse registerUser(User user) throws MissingParameterException, DoesNotExistException {
        var createdUser = userDao.createUser(user);

        UserDetailForToken userDetailForToken = new UserDetailForToken(createdUser.getEmail(), createdUser.getId(), createdUser.getRole());
        var jwtToken = jwtService.generateToken(userDetailForToken);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    /**
     * Registers a doctor.
     *
     * @param doctor the doctor to register
     * @return an authentication response containing the generated JWT token
     * @throws MissingParameterException if a required parameter is missing
     * @throws DoesNotExistException     if the doctor or user does not exist
     */
    @Transactional
    @Override
    public AuthenticationResponse registerDoctor(Doctor doctor) throws MissingParameterException, DoesNotExistException {
        var createdUser = userDao.createUser(doctor.getUser());
        doctor.setUser(createdUser);
        var createdDoctor = doctorDao.createDoctor(doctor);

        UserDetailForToken userDetailForToken = new UserDetailForToken(createdDoctor.getUser().getEmail(), createdDoctor.getId(), createdDoctor.getUser().getRole());
        var jwtToken = jwtService.generateToken(userDetailForToken);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
