package com.spring.hasdoctime.service;

import com.spring.hasdoctime.entity.AuthenticationResponse;
import com.spring.hasdoctime.entity.EmailUpdateRequestBody;
import com.spring.hasdoctime.entity.PasswordUpdateRequestBody;
import com.spring.hasdoctime.entity.User;
import com.spring.hasdoctime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdoctime.interfaces.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

/**
 * Implementation of the UserInterface for handling user-related functionality.
 */
@Service
public class UserServiceImpl implements UserInterface {
    private UserInterface userDao;

    @Autowired
    public UserServiceImpl(@Qualifier("userDaoImpl") UserInterface theUserDao) {
        this.userDao = theUserDao;
    }

    /**
     * Retrieves all users.
     *
     * @return The list of users.
     */
    @Override
    public Page<User> getAllUser(int page, int size, String sortBy, String search) {
        return userDao.getAllUser(page, size, sortBy, search);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id The ID of the user.
     * @return The user.
     * @throws DoesNotExistException If the user does not exist.
     */
    @Override
    public User getUser(int id) throws DoesNotExistException{
        return userDao.getUser(id);
    }

    /**
     * Creates a new user.
     *
     * @param user The user to create.
     * @return The created user.
     * @throws DoesNotExistException     If the user does not exist.
     * @throws MissingParameterException If a required parameter is missing.
     */
    @Override
    public User createUser(User user) throws MissingParameterException, DoesNotExistException{
        return userDao.createUser(user);
    }

    /**
     * Updates a user.
     *
     * @param id   The ID of the user to update.
     * @param user The updated user.
     * @return The updated user.
     * @throws DoesNotExistException     If the user does not exist.
     * @throws MissingParameterException If a required parameter is missing.
     */
    @Override
    public User updateUser(int id, User user) throws DoesNotExistException, MissingParameterException {
        return userDao.updateUser(id, user);
    }

    /**
     * Deletes a user.
     *
     * @param id The ID of the user to delete.
     * @return The deleted user.
     * @throws DoesNotExistException If the user does not exist.
     */
    @Override
    public User deleteUser(int id) throws DoesNotExistException{
        return userDao.deleteUser(id);
    }

    /**
     * Retrieves a user by email.
     *
     * @param email The email of the user.
     * @return The user.
     * @throws DoesNotExistException If the user does not exist.
     */
    @Override
    public User getUserByEmail(String email) throws DoesNotExistException{
        return userDao.getUserByEmail(email);
    }

    /**
     * Retrieves all patients.
     *
     * @return The list of patients.
     */
    @Override
    public Page<User> getPatients(int page, int size, String sortBy, String search) {
        return userDao.getPatients(page, size, sortBy, search);
    }

    /**
     * Retrieves patients by chronic illness ID.
     *
     * @param id The ID of the chronic illness.
     * @return The set of patients.
     * @throws DoesNotExistException If the chronic illness does not exist.
     */
    @Override
    public Page<User> getPatientsByChronicIllnessId(int id, int page, int size, String sortBy, String search) throws DoesNotExistException {
        return userDao.getPatientsByChronicIllnessId(id, page, size, sortBy, search);
    }

    @Override
    public AuthenticationResponse updateEmailOfUser(EmailUpdateRequestBody emailUpdateRequestBody) {
        return userDao.updateEmailOfUser(emailUpdateRequestBody);
    }

    @Override
    public String updatePasswordOfUser(PasswordUpdateRequestBody passwordUpdateRequestBody) throws DoesNotExistException, AuthenticationException {
        return userDao.updatePasswordOfUser(passwordUpdateRequestBody);
    }
}
