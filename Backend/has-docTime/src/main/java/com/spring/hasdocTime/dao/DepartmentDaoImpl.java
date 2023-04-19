/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.Department;
import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.entity.Symptom;
import com.spring.hasdocTime.repository.DepartmentRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.hasdocTime.interfc.DepartmentInterface;
import com.spring.hasdocTime.interfc.DoctorInterface;
import com.spring.hasdocTime.repository.DoctorRepository;
import com.spring.hasdocTime.repository.SymptomRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author arpit
 */

@Service
public class DepartmentDaoImpl implements DepartmentInterface {
    
    private DepartmentRepository departmentRepository;
    
    private SymptomRepository symptomRepository;
    
    private DoctorRepository doctorRepository;
    
    private DoctorInterface doctorDao;
    
    @Autowired
    public DepartmentDaoImpl(DepartmentRepository departmentRepository, SymptomRepository symptomRepository, DoctorRepository doctorRepository, @Qualifier("doctorDaoImpl") DoctorInterface doctorDao){
        this.departmentRepository = departmentRepository;
        this.symptomRepository = symptomRepository;
        this.doctorRepository = doctorRepository;
        this.doctorDao = doctorDao;
    }

    @Override
    public Department createDepartment(Department department) {
        List<Symptom> symptoms = department.getSymptoms();
        List<Symptom> symptomsWithData = new ArrayList<>();
        for(Symptom symptom : symptoms){
            System.out.println(symptom);
            Symptom symptomWithData;
            if(symptom.getId()!=0){
                symptomWithData = symptomRepository.findById(symptom.getId()).get();
            }
            else{
                Symptom newSymptom = symptomRepository.save(symptom);
                symptomWithData = (symptomRepository.findById(newSymptom.getId())).get();                
            }
            symptomsWithData.add(symptomWithData);
        }
        department.setSymptoms(symptomsWithData);
        List<Doctor> doctors = department.getDoctors();
        List<Doctor> doctorsWithData = new ArrayList<>();
        for(Doctor doctor : doctors){
            Doctor doctorWithData;
            if(doctor.getId()!=0){
                doctorWithData = doctorRepository.findById(doctor.getId()).get();
            }
            else{
                Doctor newDoctor = doctorDao.createDoctor(doctor);
                doctorWithData = (doctorRepository.findById(newDoctor.getId())).get();                
            }
            doctorsWithData.add(doctorWithData);
        }
        department.setDoctors(doctorsWithData);
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartment(int id) {
        Optional<Department> department = departmentRepository.findById(id);
        if(department.isPresent()){
            return department.get();
        }
        return null;
    }

    @Override
    public Department updateDepartent(int id, Department department) {
        Optional<Department> oldDepartment = departmentRepository.findById(id);
        if(oldDepartment.isPresent()){
            department.setId(id);
            return createDepartment(department);
        }
        return null;
    }

    @Override
    public Department deleteDepartment(int id) {
        Optional<Department> department = departmentRepository.findById(id);
        if(department.isPresent()){
            departmentRepository.deleteById(id);
            return department.get();
        }
        return null;
    }
    
}
