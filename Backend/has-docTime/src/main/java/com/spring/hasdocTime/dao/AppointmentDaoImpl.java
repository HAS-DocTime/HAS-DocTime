package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.AppointmentInterface;
import com.spring.hasdocTime.repository.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 * This class implements the {@link AppointmentInterface} interface and provides the implementation for CRUD operations on appointment entities.
 * It interacts with the repositories to perform the database operations.
 * This class is annotated with {@code @Service} to indicate that it is a service component in the Spring framework.
 */
@Service
public class AppointmentDaoImpl implements AppointmentInterface {

    private AppointmentRepository appointmentRepository;
    private UserRepository userRepository;
    private DoctorRepository doctorRepository;
    private TimeSlotRepository timeSlotRepository;
    private SymptomRepository symptomRepository;

    /**
     * Constructor-based dependency injection is used to inject the repositories.
     *
     * @param appointmentRepository The repository for managing appointment entities.
     * @param userRepository       The repository for managing user entities.
     * @param doctorRepository     The repository for managing doctor entities.
     * @param timeSlotRepository   The repository for managing time slot entities.
     * @param symptomRepository    The repository for managing symptom entities.
     */
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

    /**
     * Retrieves all appointments from the database.
     *
     * @return A list of all appointments.
     */
    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> allAppointments= appointmentRepository.findAll();
        for(Appointment appointment : allAppointments){
            Hibernate.initialize(appointment.getUser());
            Hibernate.initialize(appointment.getTimeSlotForAppointment());
            Hibernate.initialize(appointment.getDoctor().getUser());
            Hibernate.initialize(appointment.getDoctor().getDepartment());
        }
        return allAppointments;
    }

    /**
     * Retrieves an appointment by its ID from the database.
     *
     * @param id The ID of the appointment to retrieve.
     * @return The appointment with the specified ID.
     * @throws DoesNotExistException If the appointment with the specified ID does not exist.
     */
    @Override
    public Appointment getAppointmentById(int id) throws DoesNotExistException {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);

        if(optionalAppointment.isPresent()){
            Hibernate.initialize(optionalAppointment.get().getDoctor().getUser());
            Hibernate.initialize(optionalAppointment.get().getUser().getPatientChronicIllness());
            for(PatientChronicIllness p : optionalAppointment.get().getUser().getPatientChronicIllness()){
                Hibernate.initialize(p.getChronicIllness());
            }
            Hibernate.initialize(optionalAppointment.get().getDoctor().getDepartment());
            Hibernate.initialize(optionalAppointment.get().getTimeSlotForAppointment());
            Hibernate.initialize(optionalAppointment.get().getSymptoms());
            return optionalAppointment.get();
        }
        throw new DoesNotExistException("Appointment");
    }

    /**
     * Creates a new appointment in the database.
     *
     * @param appointment The appointment to create.
     * @return The created appointment.
     * @throws MissingParameterException If any required parameter is missing.
     * @throws DoesNotExistException     If any referenced entity does not exist.
     */
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

    /**
     * Updates an existing appointment in the database.
     *
     * @param id          The ID of the appointment to update.
     * @param appointment The updated appointment.
     * @return The updated appointment.
     * @throws DoesNotExistException     If the appointment with the specified ID does not exist.
     * @throws MissingParameterException If any required parameter is missing.
     */
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

    /**
     * Deletes an appointment from the database.
     *
     * @param id The ID of the appointment to delete.
     * @return The deleted appointment.
     * @throws DoesNotExistException If the appointment with the specified ID does not exist.
     */
    @Override
    public Appointment deleteAppointment(int id) throws DoesNotExistException{
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if(optionalAppointment.isPresent()){
            appointmentRepository.deleteById(id);
            return optionalAppointment.get();
        }
        throw new DoesNotExistException("Appointment");
    }

    /**
     * Retrieves all appointments associated with a user from the database.
     *
     * @param userId The ID of the user.
     * @return A list of appointments associated with the specified user.
     * @throws DoesNotExistException If the user with the specified ID does not exist.
     */
    @Override
    public List<Appointment> getAppointmentsByUser(int userId) throws DoesNotExistException{
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new DoesNotExistException("User");
        }
        User currentUser = user.get();
        List<Appointment> appointments = appointmentRepository.findByUser(currentUser);
        for(Appointment appointment : appointments){
            Hibernate.initialize(appointment.getDoctor().getUser());
            Hibernate.initialize(appointment.getDoctor().getDepartment());
            Hibernate.initialize(appointment.getTimeSlotForAppointment());
        }
        return appointments;
    }

    /**
     * Retrieves all appointments associated with a doctor from the database.
     *
     * @param id The ID of the doctor.
     * @return A list of appointments associated with the specified doctor.
     * @throws DoesNotExistException If the doctor with the specified ID does not exist.
     */
    @Override
    public List<Appointment> getAppointmentsOfDoctor(int id) throws DoesNotExistException {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if(doctor.isEmpty()){
            throw new DoesNotExistException("Doctor");
        }
        List<Appointment> appointments = doctor.get().getAppointments();
        for(Appointment appointment: appointments){
            Hibernate.initialize(appointment.getTimeSlotForAppointment());
            Hibernate.initialize(appointment.getUser());
        }
        return appointments;
    }
}
