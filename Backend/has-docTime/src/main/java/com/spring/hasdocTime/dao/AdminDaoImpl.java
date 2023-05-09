package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.Admin;
import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.AdminInterface;
import com.spring.hasdocTime.interfc.UserInterface;
import com.spring.hasdocTime.repository.AdminRepository;
import com.spring.hasdocTime.repository.UserRepository;
import com.spring.hasdocTime.utills.Role;
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
            return admin;
        }
        throw new DoesNotExistException("Admin");
    }

    @Override
    public Admin updateAdmin(int id, Admin admin) throws DoesNotExistException, MissingParameterException{
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if(optionalAdmin.isPresent()) {
            if(admin.getUser()==null){
                throw new MissingParameterException("User");
            }
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
            admin.setId(id); // setting admin id
            admin.getUser().setId(optionalAdmin.get().getUser().getId()); // setting user object id
            admin.getUser().setRole(Role.ADMIN);
            userRepository.save(admin.getUser()); // call userRepository's save function
            return adminRepository.findById(id).get(); // fetching Updated data
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
    public Admin createAdmin(Admin admin) throws MissingParameterException {
        if(admin.getUser()==null){
            throw new MissingParameterException("User");
        }
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
        User user;
        if(admin.getUser().getId() != 0){
             user = userRepository.findById(admin.getUser().getId()).get();     
        }
        else{
            user = userDao.createUser(admin.getUser());
        }
        admin.setUser(user);
        admin.getUser().setRole(Role.ADMIN);
        return adminRepository.save(admin);
    }
}
