/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.interfc.DoctorInterface;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author arpit
 */

@RestController
@RequestMapping("doctor")
public class DoctorController {
    
    
    private DoctorInterface doctorService;
    
    @Autowired
    public DoctorController(@Qualifier("doctorServiceImpl") DoctorInterface doctorService){
        this.doctorService = doctorService;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor){
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
    
    @RequestMapping(method = RequestMethod.GET, value = "{doctorId}")
    public ResponseEntity<Doctor> getDoctor(@PathVariable("doctorId") int id){
        Doctor doctor = doctorService.getDoctor(id);
        if(doctor==null){
            return new ResponseEntity(doctor, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(doctor, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "{doctorId}")
    public ResponseEntity<Doctor> updateDoctor(@RequestBody Doctor doctor, @PathVariable("doctorId") int id){
        Doctor updatedDoctor = doctorService.updateDoctor(id, doctor);
        if(updatedDoctor==null){
            return new ResponseEntity(updatedDoctor, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(updatedDoctor, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.DELETE, value = "{doctorId}")
    public ResponseEntity<Doctor> deleteDoctor(@PathVariable("doctorId") int id){
        Doctor doctor = doctorService.deleteDoctor(id);
        if(doctor==null){
            return new ResponseEntity(doctor, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(doctor, HttpStatus.OK);
    }
}
