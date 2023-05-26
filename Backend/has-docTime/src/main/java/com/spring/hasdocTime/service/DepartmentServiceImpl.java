/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.Department;
import java.util.List;

import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import org.springframework.data.domain.Page;
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
    public Department createDepartment(Department department) throws MissingParameterException, DoesNotExistException {
        return departmentDao.createDepartment(department);
    }

    @Override
    public Page<Department> getAllDepartments(int page, int size, String sortBy, String search) {
        return departmentDao.getAllDepartments(page, size, sortBy, search);
    }

    @Override
    public Department getDepartment(int id) throws DoesNotExistException{
        return departmentDao.getDepartment(id);
    }

    @Override
    public Department updateDepartent(int id, Department department) throws DoesNotExistException, MissingParameterException{
        return departmentDao.updateDepartent(id, department);
    }

    @Override
    public Department deleteDepartment(int id) throws DoesNotExistException {
        return departmentDao.deleteDepartment(id);
    }
    
}
