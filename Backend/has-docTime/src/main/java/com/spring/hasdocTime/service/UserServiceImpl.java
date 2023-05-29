package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserInterface {
    private UserInterface userDao;

    @Autowired
    public UserServiceImpl(@Qualifier("userDaoImpl") UserInterface theUserDao) {this.userDao = theUserDao;}



    @Override
    public Page<User> getAllUser(int page, int size, String sortBy, String search) {
        return userDao.getAllUser(page, size, sortBy, search);
    }

    @Override
    public User getUser(int id) throws DoesNotExistException{
        return userDao.getUser(id);
    }

    @Override
    public User createUser(User user) throws MissingParameterException, DoesNotExistException{
      return userDao.createUser(user);
    }

    @Override
    public User updateUser(int id, User user) throws DoesNotExistException, MissingParameterException {
        return userDao.updateUser(id, user);
    }

    @Override
    public User deleteUser(int id) throws DoesNotExistException{
      return userDao.deleteUser(id);
    }

    @Override
    public User getUserByEmail(String email) throws DoesNotExistException{
        return userDao.getUserByEmail(email);
    }

    @Override
    public Page<User> getPatients(int page, int size, String sortBy, String search) {
        return userDao.getPatients(page, size, sortBy, search);
    }

//    @Override
//    public Set<User> getPatientsByChronicIllnessId(int id) throws DoesNotExistException {
//        return userDao.getPatientsByChronicIllnessId(id);
//    }

    @Override
    public Page<User> getPatientsByChronicIllnessId(int id, int page, int size, String sortBy, String search) throws DoesNotExistException {
        return userDao.getPatientsByChronicIllnessId(id, page, size, sortBy, search);
    }
}
