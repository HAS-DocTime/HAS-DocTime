package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.TimeSlotInterface;
import com.spring.hasdocTime.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

@Service
public class TimeSlotDaoImpl implements TimeSlotInterface {

    private final Timestamp hospitalStartTime = new Timestamp(1970, 1, 1, 9,0,0,0);
    private final Timestamp hospitalEndTime = new Timestamp(2050, 12,31,21,0,0,0);

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
    public TimeSlot createTimeSlot(TimeSlot timeSlot) throws MissingParameterException, DoesNotExistException{
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
        Optional<Department> optionalDepartment = departmentRepository.findById(timeSlot.getDepartment().getId());
        if(optionalDepartment.isEmpty()){
            throw new DoesNotExistException("Department");
        }
        Department department = optionalDepartment.get();
        timeSlot.setDepartment(department);
        if(timeSlot.getAppointment() != null){
            Optional<Appointment> optionalAppointment = appointmentRepository.findById(timeSlot.getAppointment().getId());
            if(optionalAppointment.isEmpty()){
                throw new DoesNotExistException("Appointment");
            }
            Appointment appointment = optionalAppointment.get();
            timeSlot.setAppointment(appointment);
        }
        if(timeSlot.getAppointmentData() != null){
            Optional<PostAppointmentData> optionalPostAppointmentData = postAppointmentDataRepository.findById(timeSlot.getAppointmentData().getId());
            if(optionalPostAppointmentData.isEmpty()){
                throw new DoesNotExistException("Post Appointment");
            }
            PostAppointmentData postAppointmentData = optionalPostAppointmentData.get();
            timeSlot.setAppointmentData(postAppointmentData);
        }

        List<Doctor> availableDoctors = new ArrayList<>();
        if(timeSlot.getAvailableDoctors() != null){
            for(Doctor d: timeSlot.getAvailableDoctors()){
                if(d.getId() != 0){
                    Optional<Doctor> optionalAvailableDoctor = doctorRepository.findById(d.getId());
                    if(optionalAvailableDoctor.isEmpty()){
                        throw new DoesNotExistException("Doctor");
                    }
                    Doctor doctor = optionalAvailableDoctor.get();
                    availableDoctors.add(doctor);
                    timeSlot.setAvailableDoctors(availableDoctors);
                }
            }
        }

        List<Doctor> bookedDoctors = new ArrayList<>();
        if(timeSlot.getBookedDoctors() != null){
            for(Doctor d: timeSlot.getBookedDoctors()){
                if(d.getId() != 0){
                    Optional<Doctor> optionalBookedDoctor = doctorRepository.findById(d.getId());
                    if(optionalBookedDoctor.isEmpty()){
                        throw new DoesNotExistException("Doctor");
                    }
                    Doctor doctor = optionalBookedDoctor.get();
                    bookedDoctors.add(doctor);
                    timeSlot.setBookedDoctors(bookedDoctors);
                }
            }
        }
        return timeSlotRepository.save(timeSlot);
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
                Optional<Department> optionalDepartment = departmentRepository.findById(timeSlot.getDepartment().getId());
                if(optionalDepartment.isEmpty()){
                    throw new DoesNotExistException("Department");
                }
                Department department = optionalDepartment.get();
                timeSlot.setDepartment(department);
            }

            if(timeSlot.getAppointment() != null){
                Optional<Appointment> optionalAppointment = appointmentRepository.findById(timeSlot.getAppointment().getId());
                if(optionalAppointment.isEmpty()){
                    throw new DoesNotExistException("Appointment");
                }
                Appointment appointment = optionalAppointment.get();
                timeSlot.setAppointment(appointment);
            }

            if(timeSlot.getAppointmentData() != null){
                Optional<PostAppointmentData> optionalPostAppointmentData = postAppointmentDataRepository.findById(timeSlot.getAppointmentData().getId());
                if(optionalPostAppointmentData.isEmpty()){
                    throw new DoesNotExistException("Post Appointment");
                }
                PostAppointmentData postAppointmentData = optionalPostAppointmentData.get();
                timeSlot.setAppointmentData(postAppointmentData);
            }
            

            List<Doctor> availableDoctors = new ArrayList<>();
            if(timeSlot.getAvailableDoctors() != null){
                for(Doctor d: timeSlot.getAvailableDoctors()){
                    if(d.getId() != 0){
                        Optional<Doctor> optionalAvailableDoctor = doctorRepository.findById(d.getId());
                        if(optionalAvailableDoctor.isEmpty()){
                            throw new DoesNotExistException("Doctor");
                        }
                        Doctor doctor = optionalAvailableDoctor.get();
                        availableDoctors.add(doctor);
                        timeSlot.setAvailableDoctors(availableDoctors);
                    }
                }
            }

            List<Doctor> bookedDoctors = new ArrayList<>();
            if(timeSlot.getBookedDoctors() != null){
                for(Doctor d: timeSlot.getBookedDoctors()){
                    if(d.getId() != 0){
                        Optional<Doctor> optionalBookedDoctor = doctorRepository.findById(d.getId());
                        if(optionalBookedDoctor.isEmpty()){
                            throw new DoesNotExistException("Doctor");
                        }
                        Doctor doctor = optionalBookedDoctor.get();
                        bookedDoctors.add(doctor);
                        timeSlot.setBookedDoctors(bookedDoctors);
                    }
                }
            }
            timeSlotRepository.save(timeSlot);
            return timeSlot;
        }
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

    public List<TimeSlot> createTimeSlots(Department department, int index, List<TimeSlot> timeSlots){

        Timestamp timeSlotStartTime = (Timestamp) hospitalStartTime.clone();
        Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
        timeSlotStartTime.setYear(currentTimeStamp.getYear());
        timeSlotStartTime.setMonth(currentTimeStamp.getMonth());
        timeSlotStartTime.setDate(currentTimeStamp.getDate()+index);
        int timeDuration = department.getTimeDuration();
        Timestamp timeSlotEndTime = (Timestamp) timeSlotStartTime.clone();
        timeSlotEndTime.setMinutes(timeSlotEndTime.getMinutes()+timeDuration);
        Timestamp tentativeEndTime = (Timestamp) hospitalEndTime.clone();
        tentativeEndTime.setYear(currentTimeStamp.getYear());
        tentativeEndTime.setMonth(currentTimeStamp.getMonth());
        tentativeEndTime.setDate(currentTimeStamp.getDate()+index);
        int timeLimit = timeSlotEndTime.compareTo(tentativeEndTime);
        while(timeLimit<=0){
            TimeSlot timeSlot = new TimeSlot();
            timeSlot.setStartTime(timeSlotStartTime);
            timeSlot.setEndTime(timeSlotEndTime);
            timeSlot.setDepartment(department);
            timeSlots.add(timeSlot);
            timeSlotStartTime = (Timestamp) timeSlotEndTime.clone();
            timeSlotEndTime = (Timestamp) timeSlotStartTime.clone();
            timeSlotEndTime.setMinutes(timeSlotEndTime.getMinutes()+timeDuration);
            timeLimit = timeSlotEndTime.compareTo(tentativeEndTime);
        }
        return timeSlots;
    }

    public List<TimeSlot> createTimeSlotsFromDepartment(Department department){

        // clone is required as Java takes value as pass by reference and changes the value of both the objects
        int weekDays = 7;
        List<TimeSlot> timeSlots = new ArrayList<>();
        
        if(department.getTimeSlots().isEmpty()){

            for(int i=0; i<weekDays; i++){
                createTimeSlots(department, i, timeSlots);
            }
        }else{
            createTimeSlots(department, 6, timeSlots);
        }
            
        
        
//        for(int i=0; i<weekDays; i++){
//            Timestamp timeSlotStartTime = (Timestamp) hospitalStartTime.clone();
//            Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
//            timeSlotStartTime.setYear(currentTimeStamp.getYear());
//            timeSlotStartTime.setMonth(currentTimeStamp.getMonth());
//            timeSlotStartTime.setDate(currentTimeStamp.getDate()+i);
//            int timeDuration = department.getTimeDuration();
//            Timestamp timeSlotEndTime = (Timestamp) timeSlotStartTime.clone();
//            timeSlotEndTime.setMinutes(timeSlotEndTime.getMinutes()+timeDuration);
//            Timestamp tentativeEndTime = (Timestamp) hospitalEndTime.clone();
//            tentativeEndTime.setYear(currentTimeStamp.getYear());
//            tentativeEndTime.setMonth(currentTimeStamp.getMonth());
//            tentativeEndTime.setDate(currentTimeStamp.getDate()+i);
//            int timeLimit = timeSlotEndTime.compareTo(tentativeEndTime);
//            while(timeLimit<=0){
//                TimeSlot timeSlot = new TimeSlot();
//                timeSlot.setStartTime(timeSlotStartTime);
//                timeSlot.setEndTime(timeSlotEndTime);
//                timeSlot.setDepartment(department);
//                timeSlots.add(timeSlot);
//                timeSlotStartTime = (Timestamp) timeSlotEndTime.clone();
//                timeSlotEndTime = (Timestamp) timeSlotStartTime.clone();
//                timeSlotEndTime.setMinutes(timeSlotEndTime.getMinutes()+timeDuration);
//                timeLimit = timeSlotEndTime.compareTo(tentativeEndTime);
//            }
//        }
        return timeSlots;
    }

    @Scheduled(cron = "00 26 16 ? * *", zone = "Asia/Kolkata") // For Testing
//    @Scheduled(cron = "0 0 0 ? * *", zone = "Asia/Kolkata")
    public void refreshTimeSlots(){
        Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        for(TimeSlot timeSlot : timeSlots){
            if(timeSlot.getStartTime().before(currentTimeStamp)){
                timeSlotRepository.deleteById(timeSlot.getId());
            }
        }
        timeSlotRepository.deleteAll();
        List<Department> departments = departmentRepository.findAll();
        for(Department department : departments){
            Department dep = departmentRepository.findById(department.getId()).get();
            List<TimeSlot> timeslots = createTimeSlotsFromDepartment(department);
            for(TimeSlot timeSlot : timeslots){
                timeSlot.setDepartment(dep);
            }
            timeSlotRepository.saveAll(timeslots);
        }
    }
}
