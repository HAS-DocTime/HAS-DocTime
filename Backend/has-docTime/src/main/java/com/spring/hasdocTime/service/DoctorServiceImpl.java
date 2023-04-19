/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.Doctor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Doctor createDoctor(Doctor doctor) {
        return doctorDao.createDoctor(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorDao.getAllDoctors();
    }

    public Doctor getDoctor(int id) {
        return doctorDao.getDoctor(id);
    }

    public Doctor updateDoctor(int id, Doctor doctor) {
        return doctorDao.updateDoctor(id, doctor);
    }

    public Doctor deleteDoctor(int id) {
        return doctorDao.deleteDoctor(id);
    }
    
}
