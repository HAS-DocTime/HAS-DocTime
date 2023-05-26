/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.Department;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 * @author arpit
 */
public interface DepartmentInterface {
    public Department createDepartment(Department department) throws MissingParameterException, DoesNotExistException;
    public Page<Department> getAllDepartments(int page, int size, String sortBy, String search);
    public Department getDepartment(int id) throws DoesNotExistException;
    public Department updateDepartent(int id, Department department) throws DoesNotExistException, MissingParameterException;
    public Department deleteDepartment(int id) throws DoesNotExistException;
}
