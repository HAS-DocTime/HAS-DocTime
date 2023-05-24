package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.Appointment;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.AppointmentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("appointment")
@CrossOrigin(origins = "http://192.1.200.29:4200")
public class AppointmentController {

    private AppointmentInterface appointmentService;

    @Autowired
    public AppointmentController(@Qualifier("appointmentServiceImpl") AppointmentInterface appointmentService){
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        try {
            List<Appointment> allAppointments = appointmentService.getAllAppointments();
            return ResponseEntity.ok(allAppointments);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable int id) throws DoesNotExistException {
        try {
            Appointment appointment = appointmentService.getAppointmentById(id);
            return ResponseEntity.ok(appointment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment Appointment) throws MissingParameterException, DoesNotExistException{
        return appointmentService.createAppointment(Appointment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable int id, @RequestBody Appointment appointment) throws DoesNotExistException, MissingParameterException {
        try {
            Appointment updatedAppointment = appointmentService.updateAppointment(id, appointment);
            return ResponseEntity.ok(updatedAppointment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Appointment> deleteAppointment(@PathVariable int id) throws DoesNotExistException{
        Appointment appointment = appointmentService.deleteAppointment(id);
        if(appointment==null){
            return new ResponseEntity<>(appointment, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Appointment>> getAppointmentsByUser(@PathVariable int id) throws DoesNotExistException{
        List<Appointment> appointments = appointmentService.getAppointmentsByUser(id);
        if(appointments==null){
            return new ResponseEntity<>(appointments, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<Appointment>> getAppointmentsOfDoctor(@PathVariable int id) throws DoesNotExistException {
        List<Appointment> appointments = appointmentService.getAppointmentsOfDoctor(id);
        if(appointments.isEmpty()){
            return new ResponseEntity<>(appointments, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

}
