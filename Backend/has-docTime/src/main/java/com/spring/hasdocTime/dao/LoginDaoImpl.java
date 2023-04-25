package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.LoginDetail;
import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.interfc.LoginInterface;
import com.spring.hasdocTime.interfc.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginDaoImpl implements LoginInterface {

    private UserInterface userDao;

    @Autowired
    public LoginDaoImpl(@Qualifier("userDaoImpl") UserInterface userDao){
        this.userDao = userDao;
    }

    //private LoginDetail user1 = new LoginDetail("trupti@luv2code.com", "123456");

    @Override
    public User loginRequest(LoginDetail loginDetail) {

        List<User> allUser = userDao.getAllUser();
        User responseUser = null;
        for(User user : allUser){
            if(user.getEmail().equals(loginDetail.getEmail()) && user.getPassword().equals(loginDetail.getPassword())) {
                responseUser = user;
                break;
            }
        }

        return responseUser;
    }
}
