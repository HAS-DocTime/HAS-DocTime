/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdoctime.dao;

import com.spring.hasdoctime.constants.Constant;
import com.spring.hasdoctime.entity.Department;
import com.spring.hasdoctime.entity.Doctor;
import com.spring.hasdoctime.entity.Symptom;
import com.spring.hasdoctime.entity.TimeSlot;
import com.spring.hasdoctime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdoctime.repository.DepartmentRepository;
import java.util.List;
import java.util.Optional;

import com.spring.hasdoctime.repository.TimeSlotRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.spring.hasdoctime.interfaces.DepartmentInterface;
import com.spring.hasdoctime.interfaces.DoctorInterface;
import com.spring.hasdoctime.repository.SymptomRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the DepartmentInterface for performing CRUD operations on Department entities.
 */
@Service
public class DepartmentDaoImpl implements DepartmentInterface {
    
    private DepartmentRepository departmentRepository;
    
    private SymptomRepository symptomRepository;
    
    private DoctorInterface doctorDao;

    private TimeSlotDaoImpl timeSlotDao;

    private TimeSlotRepository timeSlotRepository;
    
    @Autowired
    public DepartmentDaoImpl(DepartmentRepository departmentRepository, SymptomRepository symptomRepository, @Qualifier("doctorDaoImpl") DoctorInterface doctorDao,TimeSlotDaoImpl timeSlotDao, TimeSlotRepository timeSlotRepository){
        this.departmentRepository = departmentRepository;
        this.symptomRepository = symptomRepository;
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
    @Transactional
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
        if(department.getSymptoms()==null || department.getSymptoms().isEmpty()){
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
                    throw new DoesNotExistException("Symptom");
                }
            }
        }
        department.setSymptoms(symptomsWithData);
        timeSlotRepository.deleteByDepartment(department.getId());
        List<TimeSlot> timeSlots = timeSlotDao.createTimeSlotsFromDepartment(department);
        department.setTimeSlots(timeSlots);
        return departmentRepository.save(department);
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
        throw new DoesNotExistException(Constant.DEPARTMENT);
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
    @Transactional
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
        if(department.getSymptoms()==null || department.getSymptoms().isEmpty()){
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
    @Transactional
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
