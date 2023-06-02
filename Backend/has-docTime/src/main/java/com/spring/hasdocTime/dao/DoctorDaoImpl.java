/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.Appointment;
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
import com.spring.hasdocTime.repository.UserRepository;
import com.spring.hasdocTime.utills.Role;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author arpit
 */

@Service
public class DoctorDaoImpl implements DoctorInterface {
    
    private DoctorRepository doctorRepository;
    
    private UserRepository userRepository;
    
    private DepartmentRepository departmentRepository;
    
    private AppointmentInterface appointmentDao;
    private UserInterface userDao;
    
    private PostAppointmentDataInterface postAppointmentDataDao;
    
    @Autowired
    public DoctorDaoImpl(DoctorRepository doctorRepository, UserRepository userRepository, DepartmentRepository departmentRepository, @Qualifier("appointmentDaoImpl") AppointmentInterface appointmentDao, @Qualifier("postAppointmentDataDaoImpl") PostAppointmentDataInterface postAppointmentDataDao, @Qualifier("userDaoImpl") UserInterface userDao){
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.appointmentDao = appointmentDao;
        this.postAppointmentDataDao = postAppointmentDataDao;
        this.userDao = userDao;
    }

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

    @Override
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        for(Doctor d : doctors){
            Hibernate.initialize(d.getUser());
        }
        return doctors;
    }

    @Override
    public List<Doctor> getDoctorsByDepartmentId(int id) {
        List<Doctor> doctors = doctorRepository.findDoctorsByDepartmentId(id);
        for(Doctor doctor: doctors){
            Hibernate.initialize(doctor.getUser());
        }
        return doctors;
    }

    @Override
    public Doctor getDoctor(int id) throws DoesNotExistException {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if(doctor.isPresent()){
            Hibernate.initialize(doctor.get().getUser());
            return doctor.get();
        }
        throw new DoesNotExistException("Doctor");
    }

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
}
