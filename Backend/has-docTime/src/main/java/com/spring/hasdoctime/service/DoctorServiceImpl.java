/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdoctime.service;

import com.spring.hasdoctime.entity.Doctor;

import com.spring.hasdoctime.entity.FilteredDoctorBody;
import com.spring.hasdoctime.exceptionhandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionhandling.exception.MissingParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.spring.hasdoctime.interfaces.DoctorInterface;

/**
 * Implementation of the {@link DoctorInterface} interface that provides the business logic for managing doctors.
 */
@Service
public class DoctorServiceImpl implements DoctorInterface {
    private DoctorInterface doctorDao;

    /**
     * Constructs a new {@code DoctorServiceImpl} with the specified {@link DoctorInterface} implementation.
     *
     * @param doctorDao The {@link DoctorInterface} implementation.
     */
    @Autowired
    public DoctorServiceImpl(DoctorInterface doctorDao) {
        this.doctorDao = doctorDao;
    }

    /**
     * Creates a new doctor.
     *
     * @param doctor The doctor to create.
     * @return The created doctor.
     * @throws MissingParameterException If a required parameter is missing in the new doctor.
     * @throws DoesNotExistException     If the doctor does not exist.
     */
    @Override
    public Doctor createDoctor(Doctor doctor) throws MissingParameterException, DoesNotExistException {
        return doctorDao.createDoctor(doctor);
    }

    /**
     * Retrieves all doctors.
     *
     * @return List of doctors.
     */
    @Override
    public Page<Doctor> getAllDoctors(int page, int size, String sortBy, String search) {
        return doctorDao.getAllDoctors(page, size, sortBy, search);
    }

    /**
     * Retrieves doctors by department ID.
     *
     * @param id The ID of the department.
     * @return List of doctors in the specified department.
     */
    @Override
    public Page<Doctor> getDoctorsByDepartmentId(int id, int page, int size, String sortBy, String search) {
        return doctorDao.getDoctorsByDepartmentId(id, page, size, sortBy, search);
    }

    /**
     * Retrieves a doctor by ID.
     *
     * @param id The ID of the doctor.
     * @return The doctor.
     * @throws DoesNotExistException If the doctor does not exist.
     */
    @Override
    public Doctor getDoctor(int id) throws DoesNotExistException {
        return doctorDao.getDoctor(id);
    }

    /**
     * Updates a doctor with the given ID.
     *
     * @param id     The ID of the doctor to update.
     * @param doctor The updated doctor.
     * @return The updated doctor.
     * @throws DoesNotExistException     If the doctor does not exist.
     * @throws MissingParameterException If a required parameter is missing in the updated doctor.
     */
    @Override
    public Doctor updateDoctor(int id, Doctor doctor) throws DoesNotExistException, MissingParameterException {
        return doctorDao.updateDoctor(id, doctor);
    }

    /**
     * Deletes a doctor by ID.
     *
     * @param id The ID of the doctor to delete.
     * @return The deleted doctor.
     * @throws DoesNotExistException If the doctor does not exist.
     */
    @Override
    public Doctor deleteDoctor(int id) throws DoesNotExistException {
        return doctorDao.deleteDoctor(id);
    }

    @Override
    public Page<Doctor> getDoctorsBySymptomsAndTimeSlot(FilteredDoctorBody filteredDoctorBody, int page, int size, String sortBy, String search) throws DoesNotExistException {
        return doctorDao.getDoctorsBySymptomsAndTimeSlot(filteredDoctorBody, page, size, sortBy, search);
    }
}
