package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.Admin;
import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.AdminInterface;
import com.spring.hasdocTime.interfaces.UserInterface;
import com.spring.hasdocTime.repository.AdminRepository;
import com.spring.hasdocTime.repository.UserRepository;
import com.spring.hasdocTime.utills.Role;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;

@Service
public class AdminDaoImpl implements AdminInterface {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("userDaoImpl")
    private UserInterface userDao;

    @Override
    public List<Admin> getAllAdmin(){
        return adminRepository.findAll();
    }

    @Override
    public Admin getAdmin(int id) throws DoesNotExistException {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        Admin admin = null;
        if(optionalAdmin.isPresent()){
            admin = optionalAdmin.get();
            Hibernate.initialize(admin.getUser());
            return admin;
        }
        throw new DoesNotExistException("Admin");
    }

    @Override
    public Admin updateAdmin(int id, Admin admin) throws DoesNotExistException, MissingParameterException{
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if(optionalAdmin.isPresent()) {
            Admin oldAdmin = optionalAdmin.get();
            if(admin.getUser().getPassword()==null){
                admin.getUser().setPassword(oldAdmin.getUser().getPassword());
            }
            if(admin.getUser()==null){
                throw new MissingParameterException("User");
            }
            if(admin.getUser().getId()==0){
                if(admin.getUser().getName() == null || admin.getUser().getName().equals("")){
                    throw new MissingParameterException("Name");
                }
                if(admin.getUser().getDob() == null){
                    throw new MissingParameterException("Date of Birth");
                }
                if(admin.getUser().getAge() == 0){
                    throw new MissingParameterException("Age");
                }
                if(admin.getUser().getBloodGroup()==null){
                    throw new MissingParameterException("Blood Group");
                }
                if(admin.getUser().getGender()==null){
                    throw new MissingParameterException("Gender");
                }
                if(admin.getUser().getContact()==null){
                    throw new MissingParameterException("Contact");
                }
                if(admin.getUser().getEmail()==null){
                    throw new MissingParameterException("Email");
                }
                if(admin.getUser().getPassword()==null){
                    throw new MissingParameterException("Password");
                }
            }
            admin.setId(id); // setting admin id
            admin.getUser().setId(optionalAdmin.get().getUser().getId()); // setting user object id
            admin.getUser().setRole(Role.ADMIN);
            if(userRepository.findById(admin.getUser().getId()).isEmpty()){
                throw new DoesNotExistException("User");
            }
            userRepository.save(admin.getUser()); // call userRepository's save function
            return oldAdmin; // fetching Updated data
        }
        throw new DoesNotExistException("Admin");
    }

    @Override
    public boolean deleteAdmin(int id) throws DoesNotExistException{
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if(optionalAdmin.isPresent()){
            Admin admin = optionalAdmin.get();
            admin.getUser().setRole(Role.PATIENT);
            adminRepository.deleteById(id);
            return optionalAdmin.isPresent();
        }
        throw new DoesNotExistException("Admin");
    }

    @Override
    public Admin createAdmin(Admin admin) throws MissingParameterException, DoesNotExistException {
        if(admin.getUser()==null){
            throw new MissingParameterException("User");
        }
        if(admin.getUser().getId()==0){
            if(admin.getUser().getName() == null || admin.getUser().getName().equals("")){
                throw new MissingParameterException("Name");
            }
            if(admin.getUser().getDob() == null){
                throw new MissingParameterException("Date of Birth");
            }
            if(admin.getUser().getAge() == 0){
                throw new MissingParameterException("Age");
            }
            if(admin.getUser().getBloodGroup()==null){
                throw new MissingParameterException("Blood Group");
            }
            if(admin.getUser().getGender()==null){
                throw new MissingParameterException("Gender");
            }
            if(admin.getUser().getContact()==null){
                throw new MissingParameterException("Contact");
            }
            if(admin.getUser().getEmail()==null){
                throw new MissingParameterException("Email");
            }
            if(admin.getUser().getPassword()==null){
                throw new MissingParameterException("Password");
            }
            if(admin.getUser().getRole()==null){
                throw new MissingParameterException("Role");
            }
        }
        if(admin.getUser().getId()!=0 && userRepository.findById(admin.getUser().getId()).isEmpty()){
            throw new DoesNotExistException("User");
        }
        Optional<User> user = userRepository.findById(admin.getUser().getId());
        User newUser;
        if(user.isPresent()){
            newUser = user.get();
        }
        else{
            newUser = userDao.createUser(admin.getUser());
        }
        admin.setUser(newUser);
        admin.getUser().setRole(Role.ADMIN);
        return adminRepository.save(admin);
    }
}