package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.TimeSlotInterface;
import com.spring.hasdocTime.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.*;

@Service
public class TimeSlotDaoImpl implements TimeSlotInterface {

    private final Time hospitalStartTime = new Time(9,0,0);
    private final Time hospitalEndTime = new Time(21,0,0);

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
    public TimeSlot getTimeSlotById(int id) throws DoesNotExistException {
        Optional<TimeSlot> optionalTimeSlot = timeSlotRepository.findById(id);

        if(optionalTimeSlot.isPresent()){
            return optionalTimeSlot.get();
        }
        throw new DoesNotExistException("Time Slot");
    }

    @Override
    public TimeSlot createTimeSlot(TimeSlot timeSlot) throws MissingParameterException{
        if(timeSlot.getStartTime()==null){
            throw new MissingParameterException("Start Time");
        }
        if(timeSlot.getEndTime()==null){
            throw new MissingParameterException("End Time");
        }
        if(timeSlot.getDepartment()==null){
            throw new MissingParameterException("Department");
        }
        if(timeSlot.getDepartment().getId()==0){
            throw new MissingParameterException("DepartmentId");
        }
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
    public TimeSlot updateTimeSlot(int id, TimeSlot timeSlot) throws DoesNotExistException, MissingParameterException{
        if(timeSlot.getStartTime()==null){
            throw new MissingParameterException("Start Time");
        }
        if(timeSlot.getEndTime()==null){
            throw new MissingParameterException("End Time");
        }
        if(timeSlot.getDepartment()==null){
            throw new MissingParameterException("Department");
        }
        if(timeSlot.getDepartment().getId()==0){
            throw new MissingParameterException("DepartmentId");
        }
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
        throw new DoesNotExistException("Time Slot");
    }

    @Override
    public String deleteTimeSlot(int id) throws DoesNotExistException{
        Optional<TimeSlot> optionalTimeSlot = timeSlotRepository.findById(id);
        if(optionalTimeSlot.isPresent()){
            timeSlotRepository.deleteById(id);
            return "timeSlot with id: " + id + " is deleted";
        }
        throw new DoesNotExistException("Time Slot");
    }

    public List<TimeSlot> createTimeSlotsFromDepartment(Department department){
        // clone is required as Java takes value as pass by reference and changes the value of both the objects
        Time timeSlotStartTime = (Time)hospitalStartTime.clone();
        int timeDuration = department.getTimeDuration();
        Time timeSlotEndTime = (Time)timeSlotStartTime.clone();
        timeSlotEndTime.setMinutes(timeSlotEndTime.getMinutes()+timeDuration);
        int timeLimit = timeSlotEndTime.compareTo(hospitalEndTime);
        List<TimeSlot> timeSlots = new ArrayList<>();
        while(timeLimit<=0){
            TimeSlot timeSlot = new TimeSlot();
            timeSlot.setStartTime(timeSlotStartTime);
            timeSlot.setEndTime(timeSlotEndTime);
            timeSlot.setDepartment(department);
            timeSlots.add(timeSlot);
            timeSlotStartTime = (Time)timeSlotEndTime.clone();
            timeSlotEndTime = (Time)timeSlotStartTime.clone();
            timeSlotEndTime.setMinutes(timeSlotEndTime.getMinutes()+timeDuration);
            timeLimit = timeSlotEndTime.compareTo(hospitalEndTime);
        }
        return timeSlots;
    }
}
