package com.spring.hasdoctime.service;

import com.spring.hasdoctime.entity.Admin;
import com.spring.hasdoctime.entity.AuthenticationResponse;
import com.spring.hasdoctime.entity.Doctor;
import com.spring.hasdoctime.entity.User;
import com.spring.hasdoctime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdoctime.interfaces.RegisterInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Implementation of the RegisterInterface for handling registration functionality.
 */
@Service
public class RegisterServiceImpl implements RegisterInterface {

    @Autowired
    @Qualifier("registerDaoImpl")
    private RegisterInterface registerDao;

    /**
     * Registers an admin.
     *
     * @param admin The admin to register.
     * @return The authentication response.
     * @throws DoesNotExistException     If the admin does not exist.
     * @throws MissingParameterException If a required parameter is missing.
     */
    @Override
    public AuthenticationResponse registerAdmin(Admin admin) throws DoesNotExistException, MissingParameterException {
        return registerDao.registerAdmin(admin);
    }

    /**
     * Registers a user.
     *
     * @param user The user to register.
     * @return The authentication response.
     * @throws MissingParameterException If a required parameter is missing.
     * @throws DoesNotExistException     If the user does not exist.
     */
    @Override
    public AuthenticationResponse registerUser(User user) throws MissingParameterException, DoesNotExistException {
        return registerDao.registerUser(user);
    }

    /**
     * Registers a doctor.
     *
     * @param doctor The doctor to register.
     * @return The authentication response.
     * @throws MissingParameterException If a required parameter is missing.
     * @throws DoesNotExistException     If the doctor does not exist.
     */
    @Override
    public AuthenticationResponse registerDoctor(Doctor doctor) throws MissingParameterException, DoesNotExistException {
        return registerDao.registerDoctor(doctor);
    }
}
