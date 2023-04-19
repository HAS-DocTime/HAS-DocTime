package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.Admin;

import java.util.List;

public interface AdminInterface {

    public List<Admin> getAllAdmin();

    public Admin getAdmin(int id);

    public Admin updateAdmin(int id, Admin admin);

    public boolean deleteAdmin(int id);

    public Admin createAdmin(Admin admin);
}
