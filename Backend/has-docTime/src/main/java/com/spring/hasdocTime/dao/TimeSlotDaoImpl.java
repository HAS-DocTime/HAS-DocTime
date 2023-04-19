package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.interfc.TimeSlotInterface;
import com.spring.hasdocTime.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TimeSlotDaoImpl implements TimeSlotInterface {

    private TimeSlotRepository timeSlotRepository;
    private DepartmentRepository departmentRepository;
    private DoctorRepository doctorRepository;
    private AppointmentRepository appointmentRepository;
    private PostAppointmentDataRepository postAppointmentDataRepository;


    @Autowired
    public TimeSlotDaoImpl(
            TimeSlotRepository timeSlotRepository,
            DepartmentRepository departmentRepository,
            DoctorRepository doctorRepository,
            AppointmentRepository appointmentRepository,
            PostAppointmentDataRepository postAppointmentDataRepository


    ){
        this.timeSlotRepository = timeSlotRepository;
        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
        this.appointmentRepository = appointmentRepository;
        this.postAppointmentDataRepository = postAppointmentDataRepository;
    }

    @Override
    public List<TimeSlot> getAllTimeSlots() {
        List<TimeSlot> allTimeSlots= timeSlotRepository.findAll();
        return allTimeSlots;
    }

    @Override
    public TimeSlot getTimeSlotById(int id) {
        Optional<TimeSlot> optionalTimeSlot = timeSlotRepository.findById(id);

        if(optionalTimeSlot.isPresent()){
            return optionalTimeSlot.get();
        }else{
            throw new RuntimeException("TimeSlot not found for id" + id);
        }
    }

    @Override
    public TimeSlot createTimeSlot(TimeSlot timeSlot) {

        if(timeSlot.getDepartment().getId() != 0){
            Department department = departmentRepository.findById(timeSlot.getDepartment().getId()).get();
            timeSlot.setDepartment(department);
        }
        if(timeSlot.getAppointment() != null){
            Appointment appointment = appointmentRepository.findById(timeSlot.getAppointment().getId()).get();
            timeSlot.setAppointment(appointment);
        }
        if(timeSlot.getAppointmentData() != null){
            PostAppointmentData postAppointmentData = postAppointmentDataRepository.findById(timeSlot.getAppointmentData().getId()).get();
            timeSlot.setAppointmentData(postAppointmentData);
        }

        Set<Doctor> availableDoctors = new HashSet<>();
        if(timeSlot.getAvailableDoctors() != null){
            for(Doctor d: timeSlot.getAvailableDoctors()){
                if(d.getId() != 0){
                    Doctor doctor = doctorRepository.findById(d.getId()).get();
                    availableDoctors.add(doctor);
                    timeSlot.setAvailableDoctors(availableDoctors);
                }
            }
        }

        Set<Doctor> bookedDoctors = new HashSet<>();
        if(timeSlot.getBookedDoctors() != null){
            for(Doctor d: timeSlot.getBookedDoctors()){
                if(d.getId() != 0){
                    Doctor doctor = doctorRepository.findById(d.getId()).get();
                    bookedDoctors.add(doctor);
                    timeSlot.setBookedDoctors(bookedDoctors);
                }
            }
        }
        return timeSlotRepository.save(timeSlot);
    }

    @Override
    public TimeSlot updateTimeSlot(int id, TimeSlot timeSlot) {

        Optional<TimeSlot> oldTimeSlot = timeSlotRepository.findById(id);
        if(oldTimeSlot.isPresent()) {
            TimeSlot oldTimeSlotObj = oldTimeSlot.get();
            timeSlot.setId(id);

            Department department = departmentRepository.findById(timeSlot.getDepartment().getId()).get();
            timeSlot.setDepartment(department);

            Appointment appointment = appointmentRepository.findById(timeSlot.getAppointment().getId()).get();
            timeSlot.setAppointment(appointment);

            PostAppointmentData postAppointmentData = postAppointmentDataRepository.findById(timeSlot.getAppointmentData().getId()).get();
            timeSlot.setAppointmentData(postAppointmentData);

            Set<Doctor> availableDoctors = new HashSet<>();
            for(Doctor d: timeSlot.getAvailableDoctors()){
                if(d.getId() != 0){
                    Doctor doctor = doctorRepository.findById(d.getId()).get();
                    availableDoctors.add(doctor);
                    timeSlot.setAvailableDoctors(availableDoctors);
                }
            }

            Set<Doctor> bookedDoctors = new HashSet<>();
            for(Doctor d: timeSlot.getBookedDoctors()){
                if(d.getId() != 0){
                    Doctor doctor = doctorRepository.findById(d.getId()).get();
                    bookedDoctors.add(doctor);
                    timeSlot.setBookedDoctors(bookedDoctors);
                }
            }

            timeSlotRepository.save(timeSlot);
            return timeSlot;
        }
//        TimeSlot existingTimeSlot = getTimeSlotById(id);
//        existingTimeSlot.setStartTime(timeSlot.getStartTime());
//        existingTimeSlot.setEndTime(timeSlot.getEndTime());
//        existingTimeSlot.setDepartment(timeSlot.getDepartment());
//        existingTimeSlot.setBookedDoctors(timeSlot.getBookedDoctors());
//        existingTimeSlot.setAvailableDoctors(timeSlot.getAvailableDoctors());
//        return timeSlotRepository.save(existingTimeSlot);
        return null;
    }

    @Override
    public String deleteTimeSlot(int id) {
        Optional<TimeSlot> optionalTimeSlot = timeSlotRepository.findById(id);
        if(optionalTimeSlot.isPresent()){
            timeSlotRepository.deleteById(id);
            return "timeSlot with id: " + id + " is deleted";
        }else{
            return "timeSlot with id: " + id + "doesn't exist";
        }
    }
}
