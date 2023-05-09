/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.Department;
import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.entity.Symptom;
import com.spring.hasdocTime.entity.TimeSlot;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.TimeSlotInterface;
import com.spring.hasdocTime.repository.DepartmentRepository;
import java.util.List;
import java.util.Optional;

import com.spring.hasdocTime.repository.TimeSlotRepository;
import jakarta.transaction.Transactional;
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

    private TimeSlotDaoImpl timeSlotDao;

    private TimeSlotRepository timeSlotRepository;
    
    @Autowired
    public DepartmentDaoImpl(DepartmentRepository departmentRepository, SymptomRepository symptomRepository, DoctorRepository doctorRepository, @Qualifier("doctorDaoImpl") DoctorInterface doctorDao,TimeSlotDaoImpl timeSlotDao, TimeSlotRepository timeSlotRepository){
        this.departmentRepository = departmentRepository;
        this.symptomRepository = symptomRepository;
        this.doctorRepository = doctorRepository;
        this.doctorDao = doctorDao;
        this.timeSlotDao = timeSlotDao;
        this.timeSlotRepository = timeSlotRepository;
    }

    @Override
    @Transactional
    public Department createDepartment(Department department) throws MissingParameterException{
        if(department.getName()==null || department.getName().equals("")){
            throw new MissingParameterException("Name");
        }
        if(department.getBuilding()==null || department.getBuilding().equals("")){
            throw new MissingParameterException("Building");
        }
        if(department.getTimeDuration()==0){
            throw new MissingParameterException("Time Duration");
        }
        if(department.getSymptoms()==null || department.getSymptoms().size()==0){
            throw new MissingParameterException("Symptoms");
        }
        List<Symptom> symptoms = department.getSymptoms();
        List<Symptom> symptomsWithData = new ArrayList<>();
        for(Symptom symptom : symptoms){
            Symptom symptomWithData;
            if(symptom.getId()!=0){
                Optional<Symptom> symptomObj = symptomRepository.findById(symptom.getId());
                if(symptomObj.isPresent()){
                    symptomWithData = symptomObj.get();
                    symptomsWithData.add(symptomWithData);
                }
                else{
                    throw new RuntimeException("Symptom with id " + symptom.getId() + " does not exists");
                }
            }
        }
        department.setSymptoms(symptomsWithData);
        timeSlotRepository.deleteByDepartment(department.getId());
        List<TimeSlot> timeSlots = timeSlotDao.createTimeSlotsFromDepartment(department);
        department.setTimeSlots(timeSlots);
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartment(int id) throws DoesNotExistException{
        Optional<Department> department = departmentRepository.findById(id);
        if(department.isPresent()){
            return department.get();
        }
        throw new DoesNotExistException("Department");
    }

    @Override
    public Department updateDepartent(int id, Department department) throws DoesNotExistException, MissingParameterException {
        if(department.getName()==null || department.getName().equals("")){
            throw new MissingParameterException("Name");
        }
        if(department.getBuilding()==null || department.getBuilding().equals("")){
            throw new MissingParameterException("Building");
        }
        if(department.getTimeDuration()==0){
            throw new MissingParameterException("Time Duration");
        }
        if(department.getSymptoms()==null || department.getSymptoms().size()==0){
            throw new MissingParameterException("Symptoms");
        }
        Optional<Department> oldDepartment = departmentRepository.findById(id);
        if(oldDepartment.isPresent()){
            department.setId(id);
            return createDepartment(department);
        }
        throw new DoesNotExistException("Department");
    }

    @Override
    public Department deleteDepartment(int id) throws DoesNotExistException {
        Optional<Department> department = departmentRepository.findById(id);
        if(department.isPresent()){
            for(Doctor doctor : department.get().getDoctors()){
                doctorDao.deleteDoctor(doctor.getId());
            }
            for(TimeSlot timeSlot : department.get().getTimeSlots()){
                timeSlotDao.deleteTimeSlot(timeSlot.getId());
            }
            departmentRepository.deleteById(id);
            return department.get();
        }
        throw new DoesNotExistException("Department");
    }
    
}
