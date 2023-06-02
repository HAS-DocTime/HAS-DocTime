/**
 * Interface defining operations for managing department entities.
 */
package com.spring.hasdocTime.interfaces;

import com.spring.hasdocTime.entity.Department;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;

import java.util.List;

public interface DepartmentInterface {

    /**
     * Creates a new department.
     *
     * @param department the Department object representing the new department
     * @return the created Department object
     * @throws MissingParameterException if there are missing parameters in the department object
     * @throws DoesNotExistException     if the department does not exist
     */
    public Department createDepartment(Department department) throws MissingParameterException, DoesNotExistException;

    /**
     * Retrieves a list of all departments.
     *
     * @return List of Department objects representing all departments
     */
    public List<Department> getAllDepartments();

    /**
     * Retrieves the department with the specified ID.
     *
     * @param id the ID of the department to retrieve
     * @return the Department object representing the department
     * @throws DoesNotExistException if the department does not exist
     */
    public Department getDepartment(int id) throws DoesNotExistException;

    /**
     * Updates the department with the specified ID.
     *
     * @param id         the ID of the department to update
     * @param department the updated Department object
     * @return the updated Department object
     * @throws DoesNotExistException     if the department does not exist
     * @throws MissingParameterException if there are missing parameters in the department object
     */
    public Department updateDepartent(int id, Department department) throws DoesNotExistException, MissingParameterException;

    /**
     * Deletes the department with the specified ID.
     *
     * @param id the ID of the department to delete
     * @return the deleted Department object
     * @throws DoesNotExistException if the department does not exist
     */
    public Department deleteDepartment(int id) throws DoesNotExistException;
}
