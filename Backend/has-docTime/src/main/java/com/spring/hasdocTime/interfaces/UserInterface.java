package com.spring.hasdocTime.interfaces;

import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface UserInterface {
    Page<User> getAllUser(int page, int size, String sortBy, String search);

    User getUser(int id) throws DoesNotExistException;

    User createUser(User user) throws MissingParameterException, DoesNotExistException;

    User updateUser(int id, User user) throws DoesNotExistException, MissingParameterException;

    User deleteUser(int id) throws DoesNotExistException;

    User getUserByEmail(String email) throws DoesNotExistException;

    Page<User> getPatients(int page, int size, String sortBy, String search);

    Page<User> getPatientsByChronicIllnessId(int id, int page, int size, String sortBy, String search) throws DoesNotExistException;
}
