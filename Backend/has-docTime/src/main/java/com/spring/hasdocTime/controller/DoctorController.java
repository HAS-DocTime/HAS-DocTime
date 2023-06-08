/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.DoctorInterface;
import com.spring.hasdocTime.security.jwt.JwtService;
import io.jsonwebtoken.Jwt;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * The DoctorController class handles HTTP requests related to the Doctor entity.
 */
@RestController
@RequestMapping("doctor")
@CrossOrigin(origins = "${port.address}")
public class DoctorController {

    private DoctorInterface doctorService;

    @Autowired
    public DoctorController(@Qualifier("doctorServiceImpl") DoctorInterface doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * Creates a new Doctor.
     *
     * @param doctor The Doctor object to create.
     * @return ResponseEntity containing the created Doctor and HttpStatus.CREATED if successful.
     * @throws MissingParameterException If required parameters are missing in the creation request.
     * @throws DoesNotExistException     If the Doctor with the given ID does not exist.
     */
    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<Doctor> createDoctor(@Valid @RequestBody Doctor doctor) throws MissingParameterException, DoesNotExistException {
        Doctor doc = doctorService.createDoctor(doctor);
        return new ResponseEntity<>(doc, HttpStatus.CREATED);
    }

    /**
     * Retrieves all Doctors.
     *
     * @return ResponseEntity containing a list of all Doctors and HttpStatus.OK if successful,
     * or HttpStatus.NO_CONTENT if no Doctors are found.
     */
    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity<Page<Doctor>> getAllDoctors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "user.name") String sortBy,        //Sort by user.name, user.dob(can place on age)
            @RequestParam(required = false) String search
    ){
        Page<Doctor> doctors = doctorService.getAllDoctors(page, size, sortBy, search);
        if(doctors.isEmpty()){
            return new ResponseEntity(doctors, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    /**
     * Retrieves Doctors by Department ID.
     *
     * @param id The ID of the Department to filter Doctors by.
     * @return ResponseEntity containing a list of Doctors in the specified Department and HttpStatus.OK if successful,
     * or HttpStatus.NO_CONTENT if no Doctors are found in the Department.
     */
    @RequestMapping(method = RequestMethod.GET, value = "department/{departmentId}")
    public ResponseEntity<Page<Doctor>> getDoctorsByDepartmentId(
            @PathVariable("departmentId") int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "user.name") String sortBy,
            @RequestParam(required = false) String search
    ){
        Page<Doctor> doctors = doctorService.getDoctorsByDepartmentId(id, page, size, sortBy, search);
        if(doctors.isEmpty()){
            return new ResponseEntity(doctors, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    /**
     * Retrieves a single Doctor by its ID.
     *
     * @param id The ID of the Doctor to retrieve.
     * @return ResponseEntity containing the retrieved Doctor and HttpStatus.OK if successful,
     * or HttpStatus.NOT_FOUND if the Doctor does not exist.
     * @throws AccessDeniedException If the authenticated user does not have access to the Doctor resource.
     * @throws DoesNotExistException If the Doctor with the given ID does not exist.
     */
    @RequestMapping(method = RequestMethod.GET, value = "{doctorId}")
    public ResponseEntity<Doctor> getDoctor(@PathVariable("doctorId") int id) throws AccessDeniedException, DoesNotExistException {
        Doctor doctor = doctorService.getDoctor(id);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    /**
     * Updates a Doctor.
     *
     * @param doctor The updated Doctor object.
     * @param id     The ID of the Doctor to update.
     * @return ResponseEntity containing the updated Doctor and HttpStatus.OK if successful,
     * or HttpStatus.NOT_FOUND if the Doctor does not exist.
     * @throws DoesNotExistException     If the Doctor with the given ID does not exist.
     * @throws MissingParameterException If required parameters are missing in the update request.
     */
    @RequestMapping(method = RequestMethod.PUT, value = "{doctorId}")
    public ResponseEntity<Doctor> updateDoctor(@Valid @RequestBody Doctor doctor, @PathVariable("doctorId") int id) throws DoesNotExistException, MissingParameterException {
        Doctor updatedDoctor = doctorService.updateDoctor(id, doctor);
        if (updatedDoctor == null) {
            return new ResponseEntity<>(updatedDoctor, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
    }

    /**
     * Deletes a Doctor.
     *
     * @param id The ID of the Doctor to delete.
     * @return ResponseEntity with HttpStatus.OK if the Doctor is successfully deleted,
     * or HttpStatus.NOT_FOUND if the Doctor does not exist.
     * @throws DoesNotExistException If the Doctor with the given ID does not exist.
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "{doctorId}")
    public ResponseEntity<Doctor> deleteDoctor(@PathVariable("doctorId") int id) throws DoesNotExistException {
        Doctor doctor = doctorService.deleteDoctor(id);
        if (doctor == null) {
            return new ResponseEntity<>(doctor, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }
}
