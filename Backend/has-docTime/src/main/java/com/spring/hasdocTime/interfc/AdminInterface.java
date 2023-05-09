package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.Admin;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;

import java.util.List;

public interface AdminInterface {

    public List<Admin> getAllAdmin();

    public Admin getAdmin(int id) throws DoesNotExistException;

    public Admin updateAdmin(int id, Admin admin) throws DoesNotExistException;

    public boolean deleteAdmin(int id) throws DoesNotExistException;

    public Admin createAdmin(Admin admin);
}
