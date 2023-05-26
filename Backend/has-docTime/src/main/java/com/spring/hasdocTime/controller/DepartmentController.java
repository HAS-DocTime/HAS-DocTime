/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.Department;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.DepartmentInterface;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("department")
@CrossOrigin(origins="http://192.1.200.29:4200")
public class DepartmentController {
    
    private DepartmentInterface departmentService;
    
    public DepartmentController(@Qualifier("departmentServiceImpl") DepartmentInterface departmentService){
        this.departmentService = departmentService;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) throws MissingParameterException, DoesNotExistException{
        Department dep = departmentService.createDepartment(department);
        return new ResponseEntity(dep, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity<List<Department>> getAllDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(required = false) String search
    ){
        Page<Department> departments = departmentService.getAllDepartments(page, size, sortBy, search);
        if(departments.isEmpty()){
            return new ResponseEntity(departments.getContent(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(departments.getContent(), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "{departmentId}")
    public ResponseEntity<Department> getDepartment(@PathVariable("departmentId") int id) throws DoesNotExistException{
        Department department = departmentService.getDepartment(id);
        if(department == null){
            return new ResponseEntity(department, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(department, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "{departmentId}")
    public ResponseEntity<Department> updateDepartment(@PathVariable("departmentId") int id, @RequestBody Department department) throws DoesNotExistException, MissingParameterException{
        Department updatedDepartment = departmentService.updateDepartent(id, department);
        if(updatedDepartment == null){
            return new ResponseEntity(updatedDepartment, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(updatedDepartment, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.DELETE, value = "{departmentId}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable("departmentId") int id) throws DoesNotExistException {
        Department dep = departmentService.deleteDepartment(id);
        if(dep==null){
            return new ResponseEntity(dep, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(dep, HttpStatus.OK);
    }
}
