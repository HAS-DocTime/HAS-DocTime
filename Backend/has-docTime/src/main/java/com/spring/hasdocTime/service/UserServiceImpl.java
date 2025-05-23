package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Implementation of the UserInterface for handling user-related functionality.
 */
@Service
public class UserServiceImpl implements UserInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

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
        LOGGER.info("getAllUser called with page: {}, size: {}, sortBy: {}, search: {}", page, size, sortBy, search);
        Page<User> result = userDao.getAllUser(page, size, sortBy, search);
        LOGGER.info("getAllUser returned: {}", result);
        return result;
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
        LOGGER.info("getUser called with id: {}", id);
        try {
            User result = userDao.getUser(id);
            LOGGER.info("getUser with id: {} returned: {}", id, result);
            return result;
        } catch (DoesNotExistException e) {
            LOGGER.error("DoesNotExistException in getUser with id: {}", id, e);
            throw e;
        }
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
        LOGGER.info("createUser called with user: {}", user);
        try {
            User result = userDao.createUser(user);
            LOGGER.info("createUser returned: {}", result);
            return result;
        } catch (MissingParameterException e) {
            LOGGER.error("MissingParameterException in createUser with user: {}", user, e);
            throw e;
        } catch (DoesNotExistException e) {
            LOGGER.error("DoesNotExistException in createUser with user: {}", user, e);
            throw e;
        }
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
        LOGGER.info("updateUser called with id: {} and user: {}", id, user);
        try {
            User result = userDao.updateUser(id, user);
            LOGGER.info("updateUser with id: {} returned: {}", id, result);
            return result;
        } catch (DoesNotExistException e) {
            LOGGER.error("DoesNotExistException in updateUser with id: {}", id, e);
            throw e;
        } catch (MissingParameterException e) {
            LOGGER.error("MissingParameterException in updateUser with id: {}", id, e);
            throw e;
        }
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
        LOGGER.info("deleteUser called with id: {}", id);
        try {
            User result = userDao.deleteUser(id);
            LOGGER.info("deleteUser with id: {} returned: {}", id, result);
            return result;
        } catch (DoesNotExistException e) {
            LOGGER.error("DoesNotExistException in deleteUser with id: {}", id, e);
            throw e;
        }
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
        LOGGER.info("getUserByEmail called with email: {}", email);
        try {
            User result = userDao.getUserByEmail(email);
            LOGGER.info("getUserByEmail with email: {} returned: {}", email, result);
            return result;
        } catch (DoesNotExistException e) {
            LOGGER.error("DoesNotExistException in getUserByEmail with email: {}", email, e);
            throw e;
        }
    }

    /**
     * Retrieves all patients.
     *
     * @return The list of patients.
     */
    @Override
    public Page<User> getPatients(int page, int size, String sortBy, String search) {
        LOGGER.info("getPatients called with page: {}, size: {}, sortBy: {}, search: {}", page, size, sortBy, search);
        Page<User> result = userDao.getPatients(page, size, sortBy, search);
        LOGGER.info("getPatients returned: {}", result);
        return result;
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
        LOGGER.info("getPatientsByChronicIllnessId called with id: {}, page: {}, size: {}, sortBy: {}, search: {}", id, page, size, sortBy, search);
        try {
            Page<User> result = userDao.getPatientsByChronicIllnessId(id, page, size, sortBy, search);
            LOGGER.info("getPatientsByChronicIllnessId with id: {} returned: {}", id, result);
            return result;
        } catch (DoesNotExistException e) {
            LOGGER.error("DoesNotExistException in getPatientsByChronicIllnessId with id: {}", id, e);
            throw e;
        }
    }
}
