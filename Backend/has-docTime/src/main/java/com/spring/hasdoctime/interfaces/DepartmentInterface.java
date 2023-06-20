/**
 * Interface defining operations for managing department entities.
 */
package com.spring.hasdoctime.interfaces;

import com.spring.hasdoctime.entity.Department;
import com.spring.hasdoctime.exceptionhandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionhandling.exception.MissingParameterException;
import org.springframework.data.domain.Page;

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
    public Page<Department> getAllDepartments(int page, int size, String sortBy, String search);

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

    public List<Department> getAllDepartments();
}
