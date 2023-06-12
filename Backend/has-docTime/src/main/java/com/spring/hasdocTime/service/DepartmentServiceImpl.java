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
import com.spring.hasdocTime.interfaces.DepartmentInterface;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Implementation of the {@link DepartmentInterface} interface that provides the business logic for managing departments.
 */
@Service
public class DepartmentServiceImpl implements DepartmentInterface {

    private DepartmentInterface departmentDao;

    /**
     * Constructs a new {@code DepartmentServiceImpl} with the specified {@link DepartmentInterface} implementation.
     *
     * @param departmentDao The {@link DepartmentInterface} implementation.
     */
    public DepartmentServiceImpl(@Qualifier("departmentDaoImpl") DepartmentInterface departmentDao) {
        this.departmentDao = departmentDao;
    }

    /**
     * Creates a new department.
     *
     * @param department The department to create.
     * @return The created department.
     * @throws MissingParameterException If a required parameter is missing in the new department.
     * @throws DoesNotExistException     If the department does not exist.
     */
    @Override
    public Department createDepartment(Department department) throws MissingParameterException, DoesNotExistException {
        return departmentDao.createDepartment(department);
    }

    /**
     * Retrieves all departments.
     *
     * @return List of departments.
     */
    @Override
    public Page<Department> getAllDepartments(int page, int size, String sortBy, String search) {
        return departmentDao.getAllDepartments(page, size, sortBy, search);
    }

    /**
     * Retrieves a department by its ID.
     *
     * @param id The ID of the department.
     * @return The department.
     * @throws DoesNotExistException If the department does not exist.
     */
    @Override
    public Department getDepartment(int id) throws DoesNotExistException {
        return departmentDao.getDepartment(id);
    }

    /**
     * Updates a department with the given ID.
     *
     * @param id         The ID of the department to update.
     * @param department The updated department.
     * @return The updated department.
     * @throws DoesNotExistException     If the department does not exist.
     * @throws MissingParameterException If a required parameter is missing in the updated department.
     */
    @Override
    public Department updateDepartent(int id, Department department) throws DoesNotExistException, MissingParameterException {
        return departmentDao.updateDepartent(id, department);
    }

    /**
     * Deletes a department by its ID.
     *
     * @param id The ID of the department to delete.
     * @return The deleted department.
     * @throws DoesNotExistException If the department does not exist.
     */
    @Override
    public Department deleteDepartment(int id) throws DoesNotExistException {
        return departmentDao.deleteDepartment(id);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentDao.getAllDepartments();
    }

}
