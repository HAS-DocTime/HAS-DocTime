/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.Doctor;
import java.util.List;

import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.spring.hasdocTime.interfc.DoctorInterface;

/**
 *
 * @author arpit
 */

@Service
public class DoctorServiceImpl implements DoctorInterface {
    private DoctorInterface doctorDao;
    
    @Autowired
    public DoctorServiceImpl(DoctorInterface doctorDao){
        this.doctorDao = doctorDao;
    }

    @Override
    public Doctor createDoctor(Doctor doctor) throws MissingParameterException, DoesNotExistException{
        return doctorDao.createDoctor(doctor);
    }

    @Override
    public Page<Doctor> getAllDoctors(int page, int size, String sortBy, String search) {
        return doctorDao.getAllDoctors(page, size, sortBy, search);
    }

    @Override
    public Page<Doctor> getDoctorsByDepartmentId(int id, int page, int size, String sortBy, String search) {
        return doctorDao.getDoctorsByDepartmentId(id, page, size, sortBy, search);
    }

    @Override
    public Doctor getDoctor(int id) throws DoesNotExistException {
        return doctorDao.getDoctor(id);
    }

    @Override
    public Doctor updateDoctor(int id, Doctor doctor) throws DoesNotExistException, MissingParameterException {
        return doctorDao.updateDoctor(id, doctor);
    }

    @Override
    public Doctor deleteDoctor(int id) throws DoesNotExistException{
        return doctorDao.deleteDoctor(id);
    }
    
}
