package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.security.AuthResponse;
import com.spring.hasdocTime.security.RegisterRequest;
import com.spring.hasdocTime.security.RegisterResponse;

import java.util.List;

public interface UserInterface {
    List<User> getAllUser();

    User getUser(int id);

    User createUser(User user);

    User updateUser(int id, User user);

    User deleteUser(int id);

    AuthResponse authenticate(RegisterRequest request);
    RegisterResponse register(User user);
}
