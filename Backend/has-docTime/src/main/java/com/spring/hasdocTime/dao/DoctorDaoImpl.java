/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.interfc.AppointmentInterface;
import com.spring.hasdocTime.interfc.DoctorInterface;
import com.spring.hasdocTime.interfc.PostAppointmentDataInterface;
import com.spring.hasdocTime.interfc.UserInterface;
import com.spring.hasdocTime.repository.DepartmentRepository;
import com.spring.hasdocTime.repository.DoctorRepository;
import com.spring.hasdocTime.repository.UserRepository;
import com.spring.hasdocTime.utills.Role;
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
    public Doctor createDoctor(Doctor doctor) {
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
    public Doctor getDoctor(int id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if(doctor.isPresent()){
            return doctor.get();
        }
        return null;
    }

    @Override
    public Doctor updateDoctor(int id, Doctor doctor) {
            Optional<Doctor> oldDoctor = doctorRepository.findById(id);
//        if(oldDoctor.isPresent()){
//            doctor.setId(id);
//            doctor.getUser().setId(oldDoctor.get().getUser().getId());
////            oldDoctor.get().getUser().setRole(Role.PATIENT);
////            Doctor updatedDoctor = createDoctor(doctor);
//            if(doctor.getDepartment() != null){
//                if(doctor.getDepartment().getId() != 0){
//                    Department department = departmentRepository.findById(doctor.getDepartment().getId()).get();
//                    doctor.setDepartment(department);
//                }
//            }
            if(oldDoctor.isPresent()) {
                Doctor oldDoctorObj = oldDoctor.get();
                if(doctor.getUser() != null){
                    if(doctor.getUser().getId() == 0){
                        User updatedUser = this.userDao.updateUser(oldDoctorObj.getUser().getId(), doctor.getUser());
                        oldDoctorObj.setUser(updatedUser);
                    }
                }
                if(doctor.getDepartment() != null){
                    Department department = departmentRepository.findById(doctor.getDepartment().getId()).get();
//                    department.getDoctors().add(oldDoctorObj);
                    oldDoctorObj.setDepartment(department);
                }
                if(doctor.getQualification() != null){
                    oldDoctorObj.setQualification(doctor.getQualification());
                }
                return doctorRepository.save(oldDoctorObj);
            }
            return null;
        }

    @Override
    public Doctor deleteDoctor(int id) {
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
        return null;
    } 
}
