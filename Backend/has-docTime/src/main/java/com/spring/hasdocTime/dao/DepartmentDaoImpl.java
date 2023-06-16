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
import com.spring.hasdocTime.repository.DepartmentRepository;

import java.util.*;

import com.spring.hasdocTime.repository.TimeSlotRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.spring.hasdocTime.interfaces.DepartmentInterface;
import com.spring.hasdocTime.interfaces.DoctorInterface;
import com.spring.hasdocTime.repository.DoctorRepository;
import com.spring.hasdocTime.repository.SymptomRepository;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Implementation of the DepartmentInterface for performing CRUD operations on Department entities.
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


    /**
     * Creates a new Department.
     *
     * @param department The Department to create.
     * @return The created Department.
     * @throws MissingParameterException if any required parameter is missing.
     * @throws DoesNotExistException     if a Symptom referenced by the Department does not exist.
     */
    @Override
    public Department createDepartment(Department department) throws MissingParameterException, DoesNotExistException{
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

        Set<Symptom> symptoms = department.getSymptoms();
        Set<Symptom> symptomsWithData = new HashSet<>();
        for(Symptom symptom : symptoms){
            Symptom symptomWithData;
            if(symptom.getId()!=0){
                Optional<Symptom> symptomObj = symptomRepository.findById(symptom.getId());
                if(symptomObj.isPresent()){
                    symptomWithData = symptomObj.get();
//                    symptomWithData.getDepartments().add(department);
                    symptomsWithData.add(symptomWithData);
                }
                else{
                    throw new DoesNotExistException("Symptom");
                }
            }
        }
        department.setSymptoms(symptomsWithData);

        timeSlotRepository.deleteByDepartment(department.getId());
        List<TimeSlot> timeSlots = timeSlotDao.createTimeSlotsFromDepartment(department);
        department.setTimeSlots(timeSlots);

        List<Doctor> doctors = department.getDoctors();
        List<Doctor> doctorsWithData = new ArrayList<>();
        for(Doctor doctor : doctors) {
            Doctor doctorWithData;
            if(doctor.getId()!=0){
                Optional<Doctor> doctorObj = doctorRepository.findById(doctor.getId());
                if(doctorObj.isPresent()){
                    doctorWithData = doctorObj.get();
//                    doctorWithData.setDepartment(department);
                    doctorsWithData.add(doctorWithData);
                } else {
                    throw new DoesNotExistException("Doctor");
                }
            }
        }
        department.setDoctors(doctorsWithData);
        Department departmentWithData = departmentRepository.save(department);
        return departmentWithData;
    }


    /**
     * Retrieves all Departments.
     *
     * @return A list of all Departments.
     */
    @Override
    public Page<Department> getAllDepartments(int page, int size, String sortBy, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Department> departments;
        if(search != null && !search.isEmpty()){
            //search Departments based on DepartmentName only.
            departments = departmentRepository.findAllAndNameContainsIgnoreCase(search, pageable);
        }
        else{
            departments = departmentRepository.findAll(pageable);
        }
        return departments;
    }


    /**
     * Retrieves a Department by its ID.
     *
     * @param id The ID of the Department to retrieve.
     * @return The retrieved Department.
     * @throws DoesNotExistException if the Department with the specified ID does not exist.
     */
    @Override
    public Department getDepartment(int id) throws DoesNotExistException{
        Optional<Department> department = departmentRepository.findById(id);
        if(department.isPresent()){
            Hibernate.initialize(department.get().getSymptoms());
            return department.get();
        }
        throw new DoesNotExistException("Department");
    }

    /**
     * Updates a Department.
     *
     * @param id         The ID of the Department to update.
     * @param department The updated Department.
     * @return The updated Department.
     * @throws DoesNotExistException     if the Department with the specified ID does not exist.
     * @throws MissingParameterException if any required parameter is missing.
     */
    @Override
    @Transactional
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


    /**
     * Deletes a Department by its ID.
     *
     * @param id The ID of the Department to delete.
     * @return The deleted Department.
     * @throws DoesNotExistException if the Department with the specified ID does not exist.
     */
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

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

}
