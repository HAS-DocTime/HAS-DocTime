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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("department")
@CrossOrigin(origins="*")
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
    public ResponseEntity<List<Department>> getAllDepartments(){
        List<Department> departments = departmentService.getAllDepartments();
        if(departments.isEmpty()){
            return new ResponseEntity(departments, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(departments, HttpStatus.OK);
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
