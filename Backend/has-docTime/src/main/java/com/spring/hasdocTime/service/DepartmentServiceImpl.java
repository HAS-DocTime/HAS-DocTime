/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.Department;
import java.util.List;

import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import org.springframework.stereotype.Service;
import com.spring.hasdocTime.interfc.DepartmentInterface;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author arpit
 */
@Service
public class DepartmentServiceImpl implements DepartmentInterface {
    
    private DepartmentInterface departmentDao;
    
    public DepartmentServiceImpl(@Qualifier("departmentDaoImpl") DepartmentInterface departmentDao){
        this.departmentDao = departmentDao;
    }

    @Override
    public Department createDepartment(Department department) {
        return departmentDao.createDepartment(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentDao.getAllDepartments();
    }

    @Override
    public Department getDepartment(int id) throws DoesNotExistException{
        return departmentDao.getDepartment(id);
    }

    @Override
    public Department updateDepartent(int id, Department department) throws DoesNotExistException{
        return departmentDao.updateDepartent(id, department);
    }

    @Override
    public Department deleteDepartment(int id) throws DoesNotExistException {
        return departmentDao.deleteDepartment(id);
    }
    
}
