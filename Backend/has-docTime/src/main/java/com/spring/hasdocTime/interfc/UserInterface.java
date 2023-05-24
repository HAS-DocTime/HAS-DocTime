package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserInterface {
    List<User> getAllUser();

    User getUser(int id) throws DoesNotExistException;

    User createUser(User user) throws MissingParameterException, DoesNotExistException;

    User updateUser(int id, User user) throws DoesNotExistException, MissingParameterException;

    User deleteUser(int id) throws DoesNotExistException;

    User getUserByEmail(String email) throws DoesNotExistException;

    List<User> getPatients();

    Set<User> getPatientsByChronicIllnessId(int id) throws DoesNotExistException;
}
