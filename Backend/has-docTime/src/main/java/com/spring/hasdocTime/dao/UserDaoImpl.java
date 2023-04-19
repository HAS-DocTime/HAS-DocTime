package com.spring.hasdocTime.dao;


import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.interfc.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.hasdocTime.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserDaoImpl implements UserInterface {

    private UserRepository userRepository;

    @Autowired
    public UserDaoImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        }
        return null;
    }

    @Override
    public User createUser(User user) {
        user.setId(0);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(int id, User user) {
        Optional<User> oldUser = userRepository.findById(id);
        if(oldUser.isPresent()) {
            User oldUserObj = oldUser.get();
            user.setId(oldUserObj.getId());
            userRepository.save(user);
            return user;
        }
        return null;
    }

    @Override
    public User deleteUser(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            userRepository.delete(optionalUser.get());
            return optionalUser.get();
        }
        return null;
    }
}
