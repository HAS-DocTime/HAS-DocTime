package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;

import java.util.List;
import java.util.Optional;

public interface UserInterface {
    List<User> getAllUser();

    User getUser(int id) throws DoesNotExistException;

    User createUser(User user);

    User updateUser(int id, User user) throws DoesNotExistException;

    User deleteUser(int id) throws DoesNotExistException;

    User getUserByEmail(String email);
}
