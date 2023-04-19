package com.spring.hasdocTime.service;

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
    public Admin getAdmin(int id) {
        return adminDao.getAdmin(id);
    }

    @Override
    public Admin updateAdmin(int id, Admin admin) {
        return adminDao.updateAdmin(id, admin);
    }

    @Override
    public boolean deleteAdmin(int id) {
        return adminDao.deleteAdmin(id);
    }

    @Override
    public Admin createAdmin(Admin admin) {
        return adminDao.createAdmin(admin);
    }
}
