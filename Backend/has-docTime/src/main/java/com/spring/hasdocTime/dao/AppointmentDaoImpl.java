package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.AppointmentInterface;
import com.spring.hasdocTime.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<Appointment> getAllAppointments(int page, int size, String sortBy, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Appointment> allAppointments;
        if(search != null && !search.isEmpty()){
            //search Appointments based on userName only.
            allAppointments = appointmentRepository.findAllAndUserNameContainsIgnoreCase(search, pageable);
        }
        else{
            allAppointments = appointmentRepository.findAll(pageable);

        }
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
    public Appointment createAppointment(Appointment appointment) throws MissingParameterException, DoesNotExistException{
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
        Optional<User> optionalUser = userRepository.findById(appointment.getUser().getId());
        if(optionalUser.isEmpty()){
            throw new DoesNotExistException("User");
        }
        User user = optionalUser.get();
        appointment.setUser(user);
        Optional<Doctor> optionalDoctor = doctorRepository.findById(appointment.getDoctor().getId());
        if(optionalDoctor.isEmpty()){
            throw new DoesNotExistException("Doctor");
        }
        Doctor doctor = optionalDoctor.get();
        appointment.setDoctor(doctor);
        Optional<TimeSlot> optionalTimeSlot = timeSlotRepository.findById(appointment.getTimeSlotForAppointment().getId());
        if(optionalTimeSlot.isEmpty()){
            throw new DoesNotExistException("Time Slot");
        }
        TimeSlot timeSlot = optionalTimeSlot.get();
        appointment.setTimeSlotForAppointment(timeSlot);
        List<Symptom> symptoms = new ArrayList<>();
        for(Symptom s: appointment.getSymptoms()){
            if(s.getId() != 0){
                Optional<Symptom> optionalSymptom = symptomRepository.findById(s.getId());
                if(optionalSymptom.isEmpty()){
                    throw new DoesNotExistException("Symptom");
                }
                Symptom symptom = optionalSymptom.get();
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
            Optional<User> optionalUser = userRepository.findById(appointment.getUser().getId());
            if(optionalUser.isEmpty()){
                throw new DoesNotExistException("User");
            }
            User user = optionalUser.get();
            appointment.setUser(user);
            Optional<Doctor> optionalDoctor = doctorRepository.findById(appointment.getDoctor().getId());
            if(optionalDoctor.isEmpty()){
                throw new DoesNotExistException("Doctor");
            }
            Doctor doctor = optionalDoctor.get();
            appointment.setDoctor(doctor);
            Optional<TimeSlot> optionalTimeSlot =  timeSlotRepository.findById(appointment.getTimeSlotForAppointment().getId());
            if(optionalTimeSlot.isEmpty()){
                throw new DoesNotExistException("Time Slot");
            }
            TimeSlot timeSlot = optionalTimeSlot.get();
            appointment.setTimeSlotForAppointment(timeSlot);
            List<Symptom> symptoms = new ArrayList<>();
            for(Symptom s: appointment.getSymptoms()){
                if(s.getId() != 0){
                    Optional<Symptom> optionalSymptom = symptomRepository.findById(s.getId());
                    if(optionalSymptom.isEmpty()){
                        throw new DoesNotExistException("Symptom");
                    }
                    Symptom symptom = optionalSymptom.get();
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

//    @Override
//    public List<Appointment> getAppointmentsByUser(int userId) throws DoesNotExistException{
//        Optional<User> user = userRepository.findById(userId);
//        if(user.isEmpty()){
//            throw new DoesNotExistException("User");
//        }
//        User currentUser = user.get();
//        List<Appointment> appointments = appointmentRepository.findByUser(currentUser);
//        return appointments;
//    }


    @Override
    public Page<Appointment> getAppointmentsByUser(int userId, int page, int size, String sortBy, String search) throws DoesNotExistException{
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new DoesNotExistException("User");
        }
        User currentUser = user.get();
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Appointment> appointments;
        if(search != null && !search.isEmpty()){
            appointments = appointmentRepository.findByUserAndDoctorNameContainsIgnoreCase(currentUser.getId(), search, pageable);
        }else{
            appointments = appointmentRepository.findByUser(currentUser.getId(),pageable);
        }

        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentListByUser(int userId) throws DoesNotExistException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new DoesNotExistException("User");
        }
        User currentUser = user.get();
        List<Appointment> appointments = appointmentRepository.findListByUser(userId);
        return appointments;
    }


//    @Override
//    public List<Appointment> getAppointmentsOfDoctor(int id) throws DoesNotExistException {
//        Optional<Doctor> doctor = doctorRepository.findById(id);
//        if(doctor.isEmpty()){
//            throw new DoesNotExistException("Doctor");
//        }
////        List<Appointment> appointments = doctor.get().getAppointments();
//        Doctor currentDoctor = doctor.get();
//        List<Appointment> appointments = appointmentRepository.findByDoctor(currentDoctor);
//        return appointments;
//
//    }


    @Override
    public Page<Appointment> getAppointmentsOfDoctor(int id, int page, int size, String sortBy, String search) throws DoesNotExistException {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if(doctor.isEmpty()){
            throw new DoesNotExistException("Doctor");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Appointment> appointments;
        Doctor currentDoctor = doctor.get();
        if(search != null && !search.isEmpty()){
            appointments = appointmentRepository.findByDoctorAndUserNameContainsIgnoreCase(currentDoctor.getId(), search, pageable);
        }else {
            appointments = appointmentRepository.findByDoctor(currentDoctor.getId(), pageable);
        }
        return appointments;

    }
}
