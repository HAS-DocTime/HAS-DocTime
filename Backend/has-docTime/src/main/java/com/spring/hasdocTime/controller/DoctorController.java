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
 *
 * @author arpit
 */

@RestController
@RequestMapping("doctor")
@CrossOrigin(origins = "${port.address}")
public class DoctorController {
    
    
    private DoctorInterface doctorService;

    @Autowired
    public DoctorController(@Qualifier("doctorServiceImpl") DoctorInterface doctorService){
        this.doctorService = doctorService;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<Doctor> createDoctor(@Valid @RequestBody Doctor doctor) throws MissingParameterException, DoesNotExistException{
        Doctor doc = doctorService.createDoctor(doctor);
        return new ResponseEntity(doc, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity<List<Doctor>> getAllDoctors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "user.name") String sortBy,        //Sort by user.name, user.dob(can place on age)
            @RequestParam(required = false) String search
    ){
        Page<Doctor> doctors = doctorService.getAllDoctors(page, size, sortBy, search);
        if(doctors.isEmpty()){
            return new ResponseEntity(doctors.getContent(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(doctors.getContent(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "department/{departmentId}")
    public ResponseEntity<List<Doctor>> getDoctorsByDepartmentId(
            @PathVariable("departmentId") int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "user.name") String sortBy,
            @RequestParam(required = false) String search
    ){
        Page<Doctor> doctors = doctorService.getDoctorsByDepartmentId(id, page, size, sortBy, search);
        if(doctors.isEmpty()){
            return new ResponseEntity(doctors.getContent(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(doctors.getContent(), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "{doctorId}")
    public ResponseEntity<Doctor> getDoctor(@PathVariable("doctorId") int id) throws AccessDeniedException, DoesNotExistException {
//        String authenticatedDoctorEmailId = SecurityContextHolder.getContext().getAuthentication().getName();
        Doctor doctor = doctorService.getDoctor(id);

//        if(!authenticatedDoctorEmailId.equals(doctor.getUser().getEmail())){
//            throw new AccessDeniedException("You do not have access to this resource");
//        };
        return new ResponseEntity(doctor, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "{doctorId}")
    public ResponseEntity<Doctor> updateDoctor(@Valid @RequestBody Doctor doctor, @PathVariable("doctorId") int id) throws DoesNotExistException, MissingParameterException {
        Doctor updatedDoctor = doctorService.updateDoctor(id, doctor);
        if(updatedDoctor==null){
            return new ResponseEntity(updatedDoctor, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(updatedDoctor, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.DELETE, value = "{doctorId}")
    public ResponseEntity<Doctor> deleteDoctor(@PathVariable("doctorId") int id) throws DoesNotExistException{
        Doctor doctor = doctorService.deleteDoctor(id);
        if(doctor==null){
            return new ResponseEntity(doctor, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(doctor, HttpStatus.OK);
    }
}
