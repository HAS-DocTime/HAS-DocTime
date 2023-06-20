/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdoctime.controller;

import com.spring.hasdoctime.entity.Department;
import com.spring.hasdoctime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdoctime.interfaces.DepartmentInterface;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The DepartmentController class handles HTTP requests related to the Department entity.
 */
@RestController
@RequestMapping("department")
@CrossOrigin(origins = "${port.address}")
public class DepartmentController {

    private DepartmentInterface departmentService;

    /**
     * Constructs an instance of DepartmentController with the specified DepartmentService implementation.
     *
     * @param departmentService The DepartmentService implementation to use.
     */
    public DepartmentController(@Qualifier("departmentServiceImpl") DepartmentInterface departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Creates a new Department.
     *
     * @param department The Department object to create.
     * @return ResponseEntity containing the created Department and HttpStatus.OK if successful.
     * @throws MissingParameterException If required parameters are missing in the creation request.
     * @throws DoesNotExistException     If the Department with the given ID does not exist.
     */
    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<Department> createDepartment(@Valid @RequestBody Department department) throws MissingParameterException, DoesNotExistException {
        Department dep = departmentService.createDepartment(department);
        return new ResponseEntity<>(dep, HttpStatus.OK);
    }

    /**
     * Retrieves all Departments.
     *
     * @return ResponseEntity containing a list of all Departments and HttpStatus.OK if successful,
     * or HttpStatus.NO_CONTENT if no Departments are found.
     */
    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity<Page<Department>> getAllDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(required = false) String search
    ){
        Page<Department> departments = departmentService.getAllDepartments(page, size, sortBy, search);
        if(departments.isEmpty()){
            return new ResponseEntity(departments, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/withoutPagination")
    public ResponseEntity<List<Department>> getAllDepartments(
    ){
        List<Department> departments = departmentService.getAllDepartments();
        if(departments.isEmpty()){
            return new ResponseEntity(departments, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    /**
     * Retrieves a single Department by its ID.
     *
     * @param id The ID of the Department to retrieve.
     * @return ResponseEntity containing the retrieved Department and HttpStatus.OK if successful,
     * or HttpStatus.NOT_FOUND if the Department does not exist.
     * @throws DoesNotExistException If the Department with the given ID does not exist.
     */
    @RequestMapping(method = RequestMethod.GET, value = "{departmentId}")
    public ResponseEntity<Department> getDepartment(@PathVariable("departmentId") int id) throws DoesNotExistException {
        Department department = departmentService.getDepartment(id);
        if (department == null) {
            return new ResponseEntity<>(department, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    /**
     * Updates a Department.
     *
     * @param id         The ID of the Department to update.
     * @param department The updated Department object.
     * @return ResponseEntity containing the updated Department and HttpStatus.OK if successful,
     * or HttpStatus.NOT_FOUND if the Department does not exist.
     * @throws DoesNotExistException     If the Department with the given ID does not exist.
     * @throws MissingParameterException If required parameters are missing in the update request.
     */
    @RequestMapping(method = RequestMethod.PUT, value = "{departmentId}")
    public ResponseEntity<Department> updateDepartment(@PathVariable("departmentId") int id, @Valid @RequestBody Department department) throws DoesNotExistException, MissingParameterException {
        Department updatedDepartment = departmentService.updateDepartent(id, department);
        if (updatedDepartment == null) {
            return new ResponseEntity<>(updatedDepartment, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
    }

    /**
     * Deletes a Department.
     *
     * @param id The ID of the Department to delete.
     * @return ResponseEntity with HttpStatus.OK if the Department is successfully deleted,
     * or HttpStatus.NOT_FOUND if the Department does not exist.
     * @throws DoesNotExistException If the Department with the given ID does not exist.
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "{departmentId}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable("departmentId") int id) throws DoesNotExistException {
        Department dep = departmentService.deleteDepartment(id);
        if (dep == null) {
            return new ResponseEntity<>(dep, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dep, HttpStatus.OK);
    }


}
