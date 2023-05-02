package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserInterface {
    List<User> getAllUser();

    User getUser(int id);

    User createUser(User user);

    User updateUser(int id, User user);

    User deleteUser(int id);

    User getUserByEmail(String email);
}
