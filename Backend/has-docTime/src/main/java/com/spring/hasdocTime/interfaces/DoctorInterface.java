/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.spring.hasdocTime.interfaces;

import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 * @author arpit
 */
public interface DoctorInterface {
    public Doctor createDoctor(Doctor doctor) throws MissingParameterException, DoesNotExistException;
    public Page<Doctor> getAllDoctors(int page, int size, String sortBy, String search);
    public Doctor getDoctor(int id) throws DoesNotExistException;
    public Page<Doctor> getDoctorsByDepartmentId(int id, int page, int size, String sortBy, String search);
    public Doctor updateDoctor(int id, Doctor doctor) throws DoesNotExistException, MissingParameterException;
    public Doctor deleteDoctor(int id) throws DoesNotExistException;
}
