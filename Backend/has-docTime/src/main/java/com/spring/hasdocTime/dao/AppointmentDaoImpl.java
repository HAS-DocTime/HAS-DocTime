package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.interfc.AppointmentInterface;
import com.spring.hasdocTime.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppointmentDaoImpl implements AppointmentInterface {

    private AppointmentRepository appointmentRepository;
    private UserRepository userRepository;
    private DoctorRepository doctorRepository;
    private TimeSlotRepository timeSlotRepository;
    private SymptomRepository symptomRepository;

    @Autowired
    public AppointmentDaoImpl(
            AppointmentRepository appointmentRepository,
            UserRepository userRepository,
            DoctorRepository doctorRepository,
            TimeSlotRepository timeSlotRepository,
            SymptomRepository symptomRepository
    ){
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.symptomRepository = symptomRepository;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> allAppointments= appointmentRepository.findAll();
        return allAppointments;
    }

    @Override
    public Appointment getAppointmentById(int id) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);

        if(optionalAppointment.isPresent()){
            return optionalAppointment.get();
        }else{
            throw new RuntimeException("Appointment not found for id" + id);
        }
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        if(appointment.getUser().getId() != 0){
            User user = userRepository.findById(appointment.getUser().getId()).get();
            appointment.setUser(user);
        }
        if(appointment.getDoctor().getId() != 0){
            Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId()).get();
            appointment.setDoctor(doctor);
        }
        if(appointment.getTimeSlotForAppointment().getId() != 0){
            TimeSlot timeSlot = timeSlotRepository.findById(appointment.getTimeSlotForAppointment().getId()).get();
            appointment.setTimeSlotForAppointment(timeSlot);
        }
        List<Symptom> symptoms = new ArrayList<>();
        for(Symptom s: appointment.getSymptoms()){
            if(s.getId() != 0){
                Symptom symptom = symptomRepository.findById(s.getId()).get();
                symptoms.add(symptom);
                appointment.setSymptoms(symptoms);
            }
        }
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateAppointment(int id, Appointment appointment) {
        Optional<Appointment> oldAppointment = appointmentRepository.findById(id);
        if(oldAppointment.isPresent()){
            Appointment oldAppointmentObj = oldAppointment.get();
            appointment.setId(id);
            appointment.setUser(oldAppointmentObj.getUser());
            Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId()).get();
            appointment.setDoctor(doctor);
            TimeSlot timeSlot = timeSlotRepository.findById(appointment.getTimeSlotForAppointment().getId()).get();
            appointment.setTimeSlotForAppointment(timeSlot);

            List<Symptom> symptoms = new ArrayList<>();
            for(Symptom s: appointment.getSymptoms()){
                if(s.getId() != 0){
                    Symptom symptom = symptomRepository.findById(s.getId()).get();
                    symptoms.add(symptom);
                    appointment.setSymptoms(symptoms);
                }
            }

            appointmentRepository.save(appointment);
            return appointment;
        }
//        Appointment existingAppointment = getAppointmentById(id);
//        existingAppointment.setDescription(appointment.getDescription());
//        existingAppointment.setUser(appointment.getUser());
//        existingAppointment.setDoctor(appointment.getDoctor());
//        existingAppointment.setTimeSlotForAppointment(appointment.getTimeSlotForAppointment());
//        return appointmentRepository.save(existingAppointment);
        return null;
    }

    @Override
    public String deleteAppointment(int id) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if(optionalAppointment.isPresent()){
            appointmentRepository.deleteById(id);
            return "appointment with id: " + id + " is deleted";
        }else{
            return "appointment with id: " + id + " doesn't exist";
        }
    }
}
