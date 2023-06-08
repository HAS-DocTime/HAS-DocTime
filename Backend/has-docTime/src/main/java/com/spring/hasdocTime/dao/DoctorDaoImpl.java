/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.DoctorInterface;
import com.spring.hasdocTime.entity.Department;
import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.entity.PostAppointmentData;
import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.interfaces.AppointmentInterface;
import com.spring.hasdocTime.interfaces.PostAppointmentDataInterface;
import com.spring.hasdocTime.interfaces.UserInterface;
import com.spring.hasdocTime.repository.DepartmentRepository;
import com.spring.hasdocTime.repository.DoctorRepository;
import com.spring.hasdocTime.repository.SymptomRepository;
import com.spring.hasdocTime.repository.UserRepository;
import com.spring.hasdocTime.utills.Role;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * Implementation of the DoctorInterface for performing CRUD operations on Doctor entities.
 */
@Service
public class DoctorDaoImpl implements DoctorInterface {

    private SymptomRepository symptomRepository;
    
    private DoctorRepository doctorRepository;
    
    private UserRepository userRepository;
    
    private DepartmentRepository departmentRepository;
    
    private AppointmentInterface appointmentDao;
    private UserInterface userDao;
    
    private PostAppointmentDataInterface postAppointmentDataDao;
    
    @Autowired
    public DoctorDaoImpl(DoctorRepository doctorRepository, UserRepository userRepository, DepartmentRepository departmentRepository, @Qualifier("appointmentDaoImpl") AppointmentInterface appointmentDao, @Qualifier("postAppointmentDataDaoImpl") PostAppointmentDataInterface postAppointmentDataDao, @Qualifier("userDaoImpl") UserInterface userDao, SymptomRepository symptomRepository){
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.appointmentDao = appointmentDao;
        this.postAppointmentDataDao = postAppointmentDataDao;
        this.userDao = userDao;
        this.symptomRepository = symptomRepository;
    }

    /**
     * Creates a new Doctor.
     *
     * @param doctor The Doctor to create.
     * @return The created Doctor.
     * @throws MissingParameterException if any required parameter is missing.
     * @throws DoesNotExistException     if the referenced User or Department does not exist.
     */
    @Override
    public Doctor createDoctor(Doctor doctor) throws MissingParameterException, DoesNotExistException{
        if(doctor.getUser()==null){
            throw new MissingParameterException("User");
        }
        if(doctor.getUser().getId()==0){
            throw new MissingParameterException("User Id");
        }
        if(doctor.getQualification()==null){
            throw new MissingParameterException("Qualifications");
        }
        if(doctor.getDepartment()==null){
            throw new MissingParameterException("Department");
        }
        if(doctor.getDepartment().getId()==0){
            throw new MissingParameterException("DepartmentId");
        }
        Optional<User> optionalUser = userRepository.findById(doctor.getUser().getId());
        if(optionalUser.isEmpty()){
            throw new DoesNotExistException("User");
        }
        User user = optionalUser.get();
        doctor.setUser(user);
        user.setRole(Role.DOCTOR);
        if(doctor.getDepartment() != null){
            if(doctor.getDepartment().getId() != 0){
                Optional<Department> optionalDepartment = departmentRepository.findById(doctor.getDepartment().getId());
                if(optionalDepartment.isEmpty()){
                    throw new DoesNotExistException("Department");
                }
                Department department = optionalDepartment.get();
                doctor.setDepartment(department);
            }
        }
        return doctorRepository.save(doctor);
    }

    /**
     * Retrieves all Doctors.
     *
     * @return A list of all Doctors.
     */
    @Override
    public Page<Doctor> getAllDoctors(int page, int size, String sortBy, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Doctor> doctors;
        if(search != null && !search.isEmpty()){
            //search Appointments based on userName only.
            doctors = doctorRepository.findAllAndDoctorNameContainsIgnoreCase(search, pageable);
        }
        else{
            doctors = doctorRepository.findAll(pageable);
        }
        for(Doctor d : doctors){
            Hibernate.initialize(d.getUser());
        }
        return doctors;
    }

    /**
     * Retrieves Doctors by Department ID.
     *
     * @param id The ID of the Department.
     * @return A list of Doctors in the specified Department.
     */
    @Override
    public Page<Doctor> getDoctorsByDepartmentId( int id, int page, int size, String sortBy, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Doctor> doctors;
        if(search != null && !search.isEmpty()){
            //search Doctors based on their name only. Not on the name of patients.
            doctors = doctorRepository.findDoctorsByDepartmentIdAndDoctorNameContainsIgnoreCase(search, pageable);
        }
        else{
            doctors = doctorRepository.findDoctorsByDepartmentId(id, pageable);
        }
        for(Doctor doctor: doctors){
            Hibernate.initialize(doctor.getUser());
        }
        return doctors;
    }

    /**
     * Retrieves a Doctor by its ID.
     *
     * @param id The ID of the Doctor to retrieve.
     * @return The retrieved Doctor.
     * @throws DoesNotExistException if the Doctor with the specified ID does not exist.
     */
    @Override
    public Doctor getDoctor(int id) throws DoesNotExistException {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if(doctor.isPresent()){
            Hibernate.initialize(doctor.get().getUser());
            return doctor.get();
        }
        throw new DoesNotExistException("Doctor");
    }

    /**
     * Updates a Doctor.
     *
     * @param id     The ID of the Doctor to update.
     * @param doctor The updated Doctor.
     * @return The updated Doctor.
     * @throws DoesNotExistException     if the Doctor with the specified ID does not exist.
     * @throws MissingParameterException if any required parameter is missing.
     */
    @Override
    public Doctor updateDoctor(int id, Doctor doctor) throws DoesNotExistException, MissingParameterException {
        if(doctor.getUser()==null){
            throw new MissingParameterException("User");
        }
        if(doctor.getQualification()==null){
            throw new MissingParameterException("Qualifications");
        }
        if(doctor.getDepartment()==null){
            throw new MissingParameterException("Department");
        }
        if(doctor.getDepartment().getId()==0){
            throw new MissingParameterException("Department Id");
        }
        Optional<Doctor> oldDoctor = doctorRepository.findById(id);
        if(oldDoctor.isPresent()){
            doctor.setId(id);
            oldDoctor.get().getUser().setRole(Role.PATIENT);
            User user = userDao.updateUser(oldDoctor.get().getUser().getId(), doctor.getUser());
            oldDoctor.get().setUser(user);
            Department department = departmentRepository.findById(doctor.getDepartment().getId()).get();
            oldDoctor.get().setDepartment(department);
            oldDoctor.get().setQualification(doctor.getQualification());
            return doctorRepository.save(oldDoctor.get());
        }
        throw new DoesNotExistException("Doctor");
    }

    /**
     * Deletes a Doctor by its ID.
     *
     * @param id The ID of the Doctor to delete.
     * @return The deleted Doctor.
     * @throws DoesNotExistException if the Doctor with the specified ID does not exist.
     */
    @Override
    public Doctor deleteDoctor(int id) throws DoesNotExistException{
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if(doctor.isPresent()){
            for (Appointment appointment : doctor.get().getAppointments()){
                appointmentDao.deleteAppointment(appointment.getId());
            }
            for (PostAppointmentData postAppointmentData : doctor.get().getPostAppointmentData()){
                postAppointmentDataDao.deletePostAppointmentData(postAppointmentData.getId());
            }
                doctor.get().getUser().setRole(Role.PATIENT);
                doctorRepository.deleteById(id);
                return doctor.get();
        }
        throw new DoesNotExistException("Doctor");
    }

    Timestamp manipulateTimeSlotBasedOnTimeZone(Timestamp timestamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
        calendar.setTimeInMillis(timestamp.getTime());
        calendar.add(Calendar.HOUR_OF_DAY, -5);
        calendar.add(Calendar.MINUTE, -30);
        return new Timestamp(calendar.getTimeInMillis());
    }

    @Override
    public Set<Doctor> getDoctorsBySymptomsAndTimeSlot(FilteredDoctorBody filteredDoctorBody) throws  DoesNotExistException{
        filteredDoctorBody.setTimeSlotStartTime(manipulateTimeSlotBasedOnTimeZone(filteredDoctorBody.getTimeSlotStartTime()));
        filteredDoctorBody.setTimeSlotEndTime(manipulateTimeSlotBasedOnTimeZone(filteredDoctorBody.getTimeSlotEndTime()));
        List<Symptom> givenSymptoms = filteredDoctorBody.getSymptoms();
        List<Symptom> symptoms = new ArrayList<>();
        for(Symptom symptom : givenSymptoms){
            Optional<Symptom> optionalSymptom = symptomRepository.findById(symptom.getId());
            if(optionalSymptom.isEmpty()){
                throw new DoesNotExistException("Symptom");
            }
            symptom = optionalSymptom.get();
            symptoms.add(symptom);
        }
        Set<Doctor> doctors = new HashSet<>();
        for(Symptom symptom: symptoms){
            for(Department department: symptom.getDepartments()) {;
                for (Doctor doctor : department.getDoctors()) {
                    Hibernate.initialize(doctor.getUser());
                    if(doctor.getAvailableTimeSlots()!=null){
                        List<TimeSlot> availableTimeSlotsOfDoctor = new ArrayList<>();
                        for(TimeSlot timeSlot : doctor.getAvailableTimeSlots()){
                            Timestamp timeSlotStartTime = timeSlot.getStartTime();
                            Timestamp timeSlotEndTime = timeSlot.getEndTime();
                            Timestamp doctorStartTime = filteredDoctorBody.getTimeSlotStartTime();
                            Timestamp doctorEndTime = filteredDoctorBody.getTimeSlotEndTime();
                            if( timeSlotStartTime.compareTo(doctorStartTime) >= 0  && timeSlotEndTime.compareTo(doctorEndTime) <= 0 && doctor.isAvailable()){
                                doctor.setBookedTimeSlots(null);
                                doctor.setDepartment(null);
                                doctor.setAppointments(null);
                                doctor.setPostAppointmentData(null);
                                availableTimeSlotsOfDoctor.add(timeSlot);
                                doctors.add(doctor);
                            }
                        }
                        doctor.setAvailableTimeSlots(availableTimeSlotsOfDoctor);
                    }
                }
            }
        }
        return doctors;
    }
}
