package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.interfc.TimeSlotInterface;
import com.spring.hasdocTime.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
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

        List<Doctor> availableDoctors = new ArrayList<>();
        if(timeSlot.getAvailableDoctors() != null){
            for(Doctor d: timeSlot.getAvailableDoctors()){
                if(d.getId() != 0){
                    Doctor doctor = doctorRepository.findById(d.getId()).get();
                    availableDoctors.add(doctor);
                    timeSlot.setAvailableDoctors(availableDoctors);
                }
            }
        }

        List<Doctor> bookedDoctors = new ArrayList<>();
        if(timeSlot.getBookedDoctors() != null){
            for(Doctor d: timeSlot.getBookedDoctors()){
                if(d.getId() != 0){
                    Doctor doctor = doctorRepository.findById(d.getId()).get();
                    bookedDoctors.add(doctor);
                    timeSlot.setBookedDoctors(bookedDoctors);
                }
            }
        }
        TimeSlot t =  timeSlotRepository.save(timeSlot);
        return t;
    }

    @Override
    public TimeSlot updateTimeSlot(int id, TimeSlot timeSlot) {

        Optional<TimeSlot> oldTimeSlot = timeSlotRepository.findById(id);
        if(oldTimeSlot.isPresent()) {
            TimeSlot oldTimeSlotObj = oldTimeSlot.get();
            timeSlot.setId(id);
            
            
            
            if(timeSlot.getDepartment() != null){
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
            

            List<Doctor> availableDoctors = new ArrayList<>();
            if(timeSlot.getAvailableDoctors() != null){
                for(Doctor d: timeSlot.getAvailableDoctors()){
                    if(d.getId() != 0){
                        Doctor doctor = doctorRepository.findById(d.getId()).get();
                        availableDoctors.add(doctor);
                        timeSlot.setAvailableDoctors(availableDoctors);
                    }
                }
            }
            

            List<Doctor> bookedDoctors = new ArrayList<>();
            if(timeSlot.getBookedDoctors() != null){
                for(Doctor d: timeSlot.getBookedDoctors()){
                    if(d.getId() != 0){
                        Doctor doctor = doctorRepository.findById(d.getId()).get();
                        bookedDoctors.add(doctor);
                        timeSlot.setBookedDoctors(bookedDoctors);
                    }
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

    public List<TimeSlot> createTimeSlotsFromDepartment(){
        Time hospitalStartTime = new Time(9,0,0);
        Time hospitalEndTime = new Time(21,0,0);
        List<Department> departments = departmentRepository.findAll();
        Time timeSlotStartTime = hospitalStartTime;
        Time timeSlotEndTime = hospitalStartTime;
        for(Department department : departments){
            int timeDuration = department.getTimeDuration();
//            TimeSlot timeSlot = new TimeSlot();
//            timeSlot.setStartTime(timeSlotStartTime);
//            timeSlotEndTime.setMinutes(timeSlotEndTime.getMinutes()+timeDuration);
//            timeSlot.setEndTime(timeSlotEndTime);
//            timeSlot.setDepartment(department);
        }
        return null;//To remove error
    }
}
