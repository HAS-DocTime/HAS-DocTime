/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;

import java.util.List;

/**
 *
 * @author arpit
 */
public interface DoctorInterface {
    public Doctor createDoctor(Doctor doctor) throws MissingParameterException, DoesNotExistException;
    public List<Doctor> getAllDoctors();
    public Doctor getDoctor(int id) throws DoesNotExistException;
    public List<Doctor> getDoctorsByDepartmentId(int id);
    public Doctor updateDoctor(int id, Doctor doctor) throws DoesNotExistException, MissingParameterException;
    public Doctor deleteDoctor(int id) throws DoesNotExistException;
}
