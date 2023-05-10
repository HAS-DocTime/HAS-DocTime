package com.spring.hasdocTime.service;

import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.AdminInterface;
import com.spring.hasdocTime.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminInterface{

    @Autowired
    @Qualifier("adminDaoImpl")
    private AdminInterface adminDao;

    @Override
    public List<Admin> getAllAdmin(){
        return adminDao.getAllAdmin();
    }

    @Override
    public Admin getAdmin(int id) throws DoesNotExistException {
        return adminDao.getAdmin(id);
    }

    @Override
    public Admin updateAdmin(int id, Admin admin) throws DoesNotExistException, MissingParameterException{
        return adminDao.updateAdmin(id, admin);
    }

    @Override
    public boolean deleteAdmin(int id) throws DoesNotExistException {
        return adminDao.deleteAdmin(id);
    }

    @Override
    public Admin createAdmin(Admin admin) throws MissingParameterException, DoesNotExistException{
        return adminDao.createAdmin(admin);
    }
}
