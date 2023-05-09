package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.AppointmentInterface;
import com.spring.hasdocTime.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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
    public Appointment getAppointmentById(int id) throws DoesNotExistException {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);

        if(optionalAppointment.isPresent()){
            return optionalAppointment.get();
        }
        throw new DoesNotExistException("Appointment");
    }

    @Override
    public Appointment createAppointment(Appointment appointment) throws MissingParameterException{
        if(appointment.getDescription()==null){
            throw new MissingParameterException("Description");
        }
        if(appointment.getUser()==null){
            throw new MissingParameterException("User");
        }
        if(appointment.getUser().getId()==0){
            throw new MissingParameterException("UserId");
        }
        if(appointment.getDoctor()==null){
            throw new MissingParameterException("Doctor");
        }
        if(appointment.getDoctor().getId()==0){
            throw new MissingParameterException("DoctorId");
        }
        if(appointment.getTimeSlotForAppointment()==null){
            throw new MissingParameterException("Time Slot");
        }
        if(appointment.getTimeSlotForAppointment().getId()==0){
            throw new MissingParameterException("Time Slot Id");
        }
        if(appointment.getSymptoms()==null || appointment.getSymptoms().size()==0){
            throw new MissingParameterException("Symptom");
        }
        User user = userRepository.findById(appointment.getUser().getId()).get();
        appointment.setUser(user);
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
    public Appointment updateAppointment(int id, Appointment appointment) throws DoesNotExistException, MissingParameterException{
        if(appointment.getDescription()==null){
            throw new MissingParameterException("Description");
        }
        if(appointment.getUser()==null){
            throw new MissingParameterException("User");
        }
        if(appointment.getUser().getId()==0){
            throw new MissingParameterException("UserId");
        }
        if(appointment.getDoctor()==null){
            throw new MissingParameterException("Doctor");
        }
        if(appointment.getDoctor().getId()==0){
            throw new MissingParameterException("DoctorId");
        }
        if(appointment.getTimeSlotForAppointment()==null){
            throw new MissingParameterException("Time Slot");
        }
        if(appointment.getTimeSlotForAppointment().getId()==0){
            throw new MissingParameterException("Time Slot Id");
        }
        if(appointment.getSymptoms()==null || appointment.getSymptoms().size()==0){
            throw new MissingParameterException("Symptom");
        }
        Optional<Appointment> oldAppointment = appointmentRepository.findById(id);
        if(oldAppointment.isPresent()){
            appointment.setId(id);
            appointment.setUser(userRepository.findById(appointment.getUser().getId()).get());
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
        throw new DoesNotExistException("Appointment");
    }

    @Override
    public Appointment deleteAppointment(int id) throws DoesNotExistException{
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if(optionalAppointment.isPresent()){
            appointmentRepository.deleteById(id);
            return optionalAppointment.get();
        }
        throw new DoesNotExistException("Appointment");
    }

    @Override
    public List<Appointment> getAppointmentsByUser(int userId) throws DoesNotExistException{
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new DoesNotExistException("User");
        }
        User currentUser = user.get();
        List<Appointment> appointments = appointmentRepository.findByUser(currentUser);
        return appointments;
    }
}
