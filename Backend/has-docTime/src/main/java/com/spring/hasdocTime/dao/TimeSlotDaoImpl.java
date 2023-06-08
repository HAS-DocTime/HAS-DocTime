package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.TimeSlotInterface;
import com.spring.hasdocTime.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.*;

/**
 * Implementation of the TimeSlotInterface that interacts with the TimeSlotRepository to perform CRUD operations on TimeSlot entities.
 */
@Service
public class TimeSlotDaoImpl implements TimeSlotInterface {

    private final Time hospitalStartTime = new Time(9,0,0);
    private final Time hospitalEndTime = new Time(21,0,0);

    private TimeSlotRepository timeSlotRepository;
    private DepartmentRepository departmentRepository;
    private DoctorRepository doctorRepository;
    private AppointmentRepository appointmentRepository;
    private PostAppointmentDataRepository postAppointmentDataRepository;


    /**
     * Constructs a TimeSlotDaoImpl with the necessary dependencies.
     *
     * @param timeSlotRepository         The repository for accessing and manipulating TimeSlot entities.
     * @param departmentRepository       The repository for accessing and manipulating Department entities.
     * @param doctorRepository           The repository for accessing and manipulating Doctor entities.
     * @param appointmentRepository      The repository for accessing and manipulating Appointment entities.
     * @param postAppointmentDataRepository The repository for accessing and manipulating PostAppointmentData entities.
     */
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

    /**
     * Retrieves all time slots.
     *
     * @return A list of all time slots.
     */
    @Override
    public List<TimeSlot> getAllTimeSlots() {
        List<TimeSlot> allTimeSlots= timeSlotRepository.findAll();
        return allTimeSlots;
    }

    /**
     * Retrieves a time slot by its ID.
     *
     * @param id The ID of the time slot to retrieve.
     * @return The time slot with the given ID.
     * @throws DoesNotExistException if the time slot does not exist.
     */
    @Override
    public TimeSlot getTimeSlotById(int id) throws DoesNotExistException {
        Optional<TimeSlot> optionalTimeSlot = timeSlotRepository.findById(id);

        if(optionalTimeSlot.isPresent()){
            return optionalTimeSlot.get();
        }
        throw new DoesNotExistException("Time Slot");
    }


    /**
     * Creates a new time slot.
     *
     * @param timeSlot The time slot to create.
     * @return The created time slot.
     * @throws MissingParameterException if a required parameter is missing.
     * @throws DoesNotExistException     if the department, appointment, or post-appointment data does not exist.
     */
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


    /**
     * Updates an existing time slot.
     *
     * @param id       The ID of the time slot to update.
     * @param timeSlot The updated time slot.
     * @return The updated time slot.
     * @throws DoesNotExistException     if the time slot, department, appointment, or post-appointment data does not exist.
     * @throws MissingParameterException if a required parameter is missing.
     */
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


    /**
     * Deletes a time slot.
     *
     * @param id The ID of the time slot to delete.
     * @return A message indicating the success of the operation.
     * @throws DoesNotExistException if the time slot does not exist.
     */
    @Override
    public String deleteTimeSlot(int id) throws DoesNotExistException{
        Optional<TimeSlot> optionalTimeSlot = timeSlotRepository.findById(id);
        if(optionalTimeSlot.isPresent()){
            timeSlotRepository.deleteById(id);
            return "timeSlot with id: " + id + " is deleted";
        }
        throw new DoesNotExistException("Time Slot");
    }

    /**
     * Creates time slots from a department.
     *
     * @param department The department to create time slots for.
     * @return The list of created time slots.
     */
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
