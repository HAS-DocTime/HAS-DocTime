package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
    public List<User> getAllUser() {
        return userDao.getAllUser();
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
    public List<User> getPatients() {
        return userDao.getPatients();
    }

    /**
     * Retrieves patients by chronic illness ID.
     *
     * @param id The ID of the chronic illness.
     * @return The set of patients.
     * @throws DoesNotExistException If the chronic illness does not exist.
     */
    @Override
    public Set<User> getPatientsByChronicIllnessId(int id) throws DoesNotExistException {
        return userDao.getPatientsByChronicIllnessId(id);
    }
}
