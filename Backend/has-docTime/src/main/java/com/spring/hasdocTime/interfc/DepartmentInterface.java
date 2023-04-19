/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.Department;
import java.util.List;

/**
 *
 * @author arpit
 */
public interface DepartmentInterface {
    public Department createDepartment(Department department);
    public List<Department> getAllDepartments();
    public Department getDepartment(int id);
    public Department updateDepartent(int id, Department department);
    public Department deleteDepartment(int id);
}
