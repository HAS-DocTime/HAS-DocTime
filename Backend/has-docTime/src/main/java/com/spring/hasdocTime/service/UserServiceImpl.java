package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.interfc.UserInterface;
import com.spring.hasdocTime.security.AuthResponse;
import com.spring.hasdocTime.security.RegisterRequest;
import com.spring.hasdocTime.security.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserInterface {
    private UserInterface userDao;

    @Autowired
    public UserServiceImpl(@Qualifier("userDaoImpl") UserInterface theUserDao) {this.userDao = theUserDao;}



    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    @Override
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    public User createUser(User user) {
      return userDao.createUser(user);
    }

    @Override
    public User updateUser(int id, User user) {
        return userDao.updateUser(id, user);
    }

    @Override
    public User deleteUser(int id) {
      return userDao.deleteUser(id);
    }

    @Override
    public AuthResponse authenticate(RegisterRequest request) {
        return userDao.authenticate(request);
    }

    @Override
    public RegisterResponse register(User user) {
        return userDao.register(user);
    }
}
