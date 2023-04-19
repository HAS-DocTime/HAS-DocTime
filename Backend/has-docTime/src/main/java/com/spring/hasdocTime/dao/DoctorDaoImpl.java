/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.interfc.DoctorInterface;
import com.spring.hasdocTime.entity.Department;
import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.repository.DepartmentRepository;
import com.spring.hasdocTime.repository.DoctorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.hasdocTime.repository.UserRepository;
import com.spring.hasdocTime.utills.Role;
import jakarta.persistence.EntityManager;

/**
 *
 * @author arpit
 */

@Service
public class DoctorDaoImpl implements DoctorInterface {
    
    private DoctorRepository doctorRepository;
    
    private UserRepository userRepository;
    
    private DepartmentRepository departmentRepository;
    
    @Autowired
    public DoctorDaoImpl(DoctorRepository doctorRepository, UserRepository userRepository, DepartmentRepository departmentRepository){
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Doctor createDoctor(Doctor doctor) {
        if(doctor.getUser().getId()!=0){
            User user = userRepository.findById(doctor.getUser().getId()).get();
            doctor.setUser(user);
            user.setRole(Role.DOCTOR);
        }
        else{
            doctor.getUser().setRole(Role.DOCTOR);
        }
        if(doctor.getDepartment().getId() != 0){
            Department department = departmentRepository.findById(doctor.getDepartment().getId()).get();
            doctor.setDepartment(department);
        }
        return doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctor(int id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if(doctor.isPresent()){
            return doctor.get();
        }
        return null;
    }

    @Override
    public Doctor updateDoctor(int id, Doctor doctor) {
        Optional<Doctor> oldDoctor = doctorRepository.findById(id);
        if(oldDoctor.isPresent()){
            doctor.setId(id);
            Doctor updatedDoctor = createDoctor(doctor);
            return updatedDoctor;
        }
        return null;
    }

    @Override
    public Doctor deleteDoctor(int id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if(doctor.isPresent()){
            doctorRepository.deleteById(id);
            return doctor.get();
        }
        return null;
    } 
}
