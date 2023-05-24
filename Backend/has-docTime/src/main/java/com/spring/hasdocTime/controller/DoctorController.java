/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.DoctorInterface;
import com.spring.hasdocTime.security.jwt.JwtService;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author arpit
 */

@RestController
@RequestMapping("doctor")
@CrossOrigin(origins = "http://192.1.200.29:4200")
public class DoctorController {
    
    
    private DoctorInterface doctorService;

    @Autowired
    public DoctorController(@Qualifier("doctorServiceImpl") DoctorInterface doctorService){
        this.doctorService = doctorService;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) throws MissingParameterException, DoesNotExistException{
        Doctor doc = doctorService.createDoctor(doctor);
        return new ResponseEntity(doc, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity<List<Doctor>> getAllDoctors(){
        List<Doctor> doctors = doctorService.getAllDoctors();
        if(doctors.isEmpty()){
            return new ResponseEntity(doctors, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(doctors, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "department/{departmentId}")
    public ResponseEntity<List<Doctor>> getDoctorsByDepartmentId(@PathVariable("departmentId") int id){
        List<Doctor> doctors = doctorService.getDoctorsByDepartmentId(id);
        if(doctors.isEmpty()){
            return new ResponseEntity(doctors, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(doctors, HttpStatus.OK);
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
    public ResponseEntity<Doctor> updateDoctor(@RequestBody Doctor doctor, @PathVariable("doctorId") int id) throws DoesNotExistException, MissingParameterException {
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
