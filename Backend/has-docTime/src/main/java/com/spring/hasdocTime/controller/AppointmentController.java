package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.Appointment;
import com.spring.hasdocTime.interfc.AppointmentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("appointment")
@CrossOrigin(value = "*")
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
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable int id) {
        try {
            Appointment appointment = appointmentService.getAppointmentById(id);
            return ResponseEntity.ok(appointment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment Appointment) {
        return appointmentService.createAppointment(Appointment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable int id, @RequestBody Appointment appointment) {
        try {
            Appointment updatedAppointment = appointmentService.updateAppointment(id, appointment);
            return ResponseEntity.ok(updatedAppointment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable int id) {
        String result = appointmentService.deleteAppointment(id);
        if (result.equals("appointment with id: " + id + " is deleted")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
