/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.Appointment;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.DoctorInterface;
import com.spring.hasdocTime.entity.Department;
import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.entity.PostAppointmentData;
import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.interfc.AppointmentInterface;
import com.spring.hasdocTime.interfc.PostAppointmentDataInterface;
import com.spring.hasdocTime.repository.DepartmentRepository;
import com.spring.hasdocTime.repository.DoctorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.hasdocTime.repository.UserRepository;
import com.spring.hasdocTime.utills.Role;
import org.springframework.beans.factory.annotation.Qualifier;

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
    
    private PostAppointmentDataInterface postAppointmentDataDao;
    
    @Autowired
    public DoctorDaoImpl(DoctorRepository doctorRepository, UserRepository userRepository, DepartmentRepository departmentRepository, @Qualifier("appointmentDaoImpl") AppointmentInterface appointmentDao, @Qualifier("postAppointmentDataDaoImpl") PostAppointmentDataInterface postAppointmentDataDao){
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.appointmentDao = appointmentDao;
        this.postAppointmentDataDao = postAppointmentDataDao;
    }

    @Override
    public Doctor createDoctor(Doctor doctor) throws MissingParameterException{
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
        if(doctor.getUser().getId()!=0){
            User user = userRepository.findById(doctor.getUser().getId()).get();
            doctor.setUser(user);
            user.setRole(Role.DOCTOR);
        }
        if(doctor.getDepartment() != null){
            if(doctor.getDepartment().getId() != 0){
                Department department = departmentRepository.findById(doctor.getDepartment().getId()).get();
                doctor.setDepartment(department);
            }
        }
        return doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctor(int id) throws DoesNotExistException {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if(doctor.isPresent()){
            return doctor.get();
        }
        throw new DoesNotExistException("Doctor");
    }

    @Override
    public Doctor updateDoctor(int id, Doctor doctor) throws DoesNotExistException, MissingParameterException {
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
        Optional<Doctor> oldDoctor = doctorRepository.findById(id);
        if(oldDoctor.isPresent()){
            doctor.setId(id);
            oldDoctor.get().getUser().setRole(Role.PATIENT);
            Doctor updatedDoctor = createDoctor(doctor);
            return updatedDoctor;
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
