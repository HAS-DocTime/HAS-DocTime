package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.Appointment;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.AppointmentInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("appointment")
@CrossOrigin(origins = "${port.address}")
public class AppointmentController {

    private AppointmentInterface appointmentService;

    @Autowired
    public AppointmentController(@Qualifier("appointmentServiceImpl") AppointmentInterface appointmentService){
        this.appointmentService = appointmentService;
    }

    @GetMapping("")
    public ResponseEntity<List<Appointment>> getAllAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "user.name") String sortBy,       //sort by user.name, doctor.user.name, timeSlotForAppointment.startTime
            @RequestParam(required = false) String search
    ) {
        try {
            Page<Appointment> allAppointments = appointmentService.getAllAppointments(page, size, sortBy, search);
            return ResponseEntity.ok(allAppointments.getContent());
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
    public ResponseEntity<List<Appointment>> getAppointmentsByUser(
            @PathVariable int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "doctor.user.name") String sortBy,     //sort by doctor.user.name, timeSlotForAppointment.startTime        &sortDirection=desc to sort ascending or descending
            @RequestParam(required = false) String search
    ) throws DoesNotExistException{
        Page<Appointment> appointments = appointmentService.getAppointmentsByUser(id, page, size, sortBy, search);
        if(appointments==null){
            return new ResponseEntity<>(appointments.getContent(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments.getContent(), HttpStatus.OK);
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<Appointment>> getAppointmentsOfDoctor(
            @PathVariable int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "user.name") String sortBy,     //sort by user.name, timeSlotForAppointment.startTime
            @RequestParam(required = false) String search
    ) throws DoesNotExistException {
        Page<Appointment> appointments = appointmentService.getAppointmentsOfDoctor(id, page, size, sortBy, search);
        if(appointments.isEmpty()){
            return new ResponseEntity<>(appointments.getContent(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appointments.getContent(), HttpStatus.OK);
    }

}
