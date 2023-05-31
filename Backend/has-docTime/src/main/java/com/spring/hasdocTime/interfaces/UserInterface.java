/**
 * Interface defining operations for users.
 */
package com.spring.hasdocTime.interfaces;

import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;

import java.util.List;
import java.util.Set;

public interface UserInterface {

    /**
     * Retrieves all users.
     *
     * @return a list of User objects representing all users
     */
    List<User> getAllUser();

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user
     * @return the User object representing the retrieved user
     * @throws DoesNotExistException if the user does not exist
     */
    User getUser(int id) throws DoesNotExistException;

    /**
     * Creates a new user.
     *
     * @param user the User object representing the user to be created
     * @return the User object representing the created user
     * @throws MissingParameterException if required parameters are missing
     * @throws DoesNotExistException    if related entities do not exist
     */
    User createUser(User user) throws MissingParameterException, DoesNotExistException;

    /**
     * Updates a user.
     *
     * @param id   the ID of the user to be updated
     * @param user the User object representing the updated user
     * @return the User object representing the updated user
     * @throws DoesNotExistException    if the user does not exist
     * @throws MissingParameterException if required parameters are missing
     */
    User updateUser(int id, User user) throws DoesNotExistException, MissingParameterException;

    /**
     * Deletes a user.
     *
     * @param id the ID of the user to be deleted
     * @return the User object representing the deleted user
     * @throws DoesNotExistException if the user does not exist
     */
    User deleteUser(int id) throws DoesNotExistException;

    /**
     * Retrieves a user by email.
     *
     * @param email the email of the user
     * @return the User object representing the retrieved user
     * @throws DoesNotExistException if the user does not exist
     */
    User getUserByEmail(String email) throws DoesNotExistException;

    /**
     * Retrieves all patients.
     *
     * @return a list of User objects representing all patients
     */
    List<User> getPatients();

    /**
     * Retrieves patients by chronic illness ID.
     *
     * @param id the ID of the chronic illness
     * @return a set of User objects representing patients with the specified chronic illness
     * @throws DoesNotExistException if the chronic illness does not exist
     */
    Set<User> getPatientsByChronicIllnessId(int id) throws DoesNotExistException;
}
