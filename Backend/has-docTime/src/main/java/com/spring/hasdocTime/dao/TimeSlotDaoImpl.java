package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.TimeSlotInterface;
import com.spring.hasdocTime.repository.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Implementation of the TimeSlotInterface that interacts with the TimeSlotRepository to perform CRUD operations on TimeSlot entities.
 */
@Service
public class TimeSlotDaoImpl implements TimeSlotInterface {

    private final Timestamp hospitalStartTime = new Timestamp(1970, 1, 1, 9,0,0,0);
    private final Timestamp hospitalEndTime = new Timestamp(2050, 12,31,21,0,0,0);

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
    @Transactional
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
            Set<Appointment> appointmentList = new HashSet<>();
            for(Appointment appointment : timeSlot.getAppointment()){
                Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointment.getId());
                if(optionalAppointment.isEmpty()){
                    throw new DoesNotExistException("Appointment");
                }
                Appointment foundAppointment = optionalAppointment.get();
                appointmentList.add(foundAppointment);
            }
            timeSlot.setAppointment(appointmentList);

        }
        if(timeSlot.getAppointmentData() != null){
            Set<PostAppointmentData> postAppointmentDataList = new HashSet<>();
            for(PostAppointmentData postAppointmentData : timeSlot.getAppointmentData()) {
                Optional<PostAppointmentData> optionalPostAppointmentData = postAppointmentDataRepository.findById(postAppointmentData.getId());
                if (optionalPostAppointmentData.isEmpty()) {
                    throw new DoesNotExistException("Post Appointment");
                }
                PostAppointmentData foundPostAppointmentData = optionalPostAppointmentData.get();
                postAppointmentDataList.add(foundPostAppointmentData);
            }
            timeSlot.setAppointmentData(postAppointmentDataList);
        }

        Set<Doctor> availableDoctors = new HashSet<>();
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

        Set<Doctor> bookedDoctors = new HashSet<>();
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
    @Transactional
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
                Set<Appointment> appointments = new HashSet<>();
                for(Appointment appointment : timeSlot.getAppointment()){
                    Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointment.getId());
                    if(optionalAppointment.isEmpty()){
                        throw new DoesNotExistException("Appointment");
                    }
                    Appointment foundAppointment = optionalAppointment.get();
                    appointments.add(foundAppointment);
                }
                timeSlot.setAppointment(appointments);
            }

            if(timeSlot.getAppointmentData() != null){
                Set<PostAppointmentData> postAppointmentDataList = new HashSet<>();
                for(PostAppointmentData postAppointmentData : timeSlot.getAppointmentData()){
                    Optional<PostAppointmentData> optionalPostAppointmentData = postAppointmentDataRepository.findById(postAppointmentData.getId());
                    if(optionalPostAppointmentData.isEmpty()){
                        throw new DoesNotExistException("Post Appointment");
                    }
                    PostAppointmentData foundPostAppointmentData = optionalPostAppointmentData.get();
                    postAppointmentDataList.add(foundPostAppointmentData);
                }
                timeSlot.setAppointmentData(postAppointmentDataList);
            }
            Set<Doctor> availableDoctors = new HashSet<>();
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

            Set<Doctor> bookedDoctors = new HashSet<>();
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
    @Transactional
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
    @Transactional
    public List<TimeSlot> createTimeSlots(Department department, int index, List<TimeSlot> timeSlots){
        Timestamp timeSlotStartTime;
        if(index == 0){
            timeSlotStartTime = roundTheTimeSlot(new Timestamp(System.currentTimeMillis()));
        }else{
            timeSlotStartTime = (Timestamp) hospitalStartTime.clone();
        }

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
            TimeSlot checkTimeSlot = timeSlotRepository.checkIfTimeSlotExists(timeSlot.getStartTime(), timeSlot.getEndTime(), timeSlot.getDepartment().getId());
            if(checkTimeSlot == null){
                System.out.println("Adding TimeSlot");
                timeSlots.add(timeSlot);
            }
            timeSlotStartTime = (Timestamp) timeSlotEndTime.clone();
            timeSlotEndTime = (Timestamp) timeSlotStartTime.clone();
            timeSlotEndTime.setMinutes(timeSlotEndTime.getMinutes()+timeDuration);
            timeLimit = timeSlotEndTime.compareTo(tentativeEndTime);
        }
        return timeSlots;
    }

    @Transactional
    public List<TimeSlot> createTimeSlotsFromDepartment(Department department){

        // clone is required as Java takes value as pass by reference and changes the value of both the objects
        int weekDays = 7;
        List<TimeSlot> timeSlots = new ArrayList<>();
        if(department.getTimeSlots().isEmpty()){
            for(int i=0; i<weekDays; i++){
                timeSlots = createTimeSlots(department, i, timeSlots);
            }
        }else{
            timeSlots = createTimeSlots(department, 6, timeSlots);
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

    @Transactional
//    @Scheduled(cron = "20 42 13 ? * *", zone = "Asia/Kolkata") // For Testing
    @Scheduled(cron = "0 0 0 ? * *", zone = "Asia/Kolkata")
    public void refreshTimeSlots(){
        Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        List<TimeSlot> timeSlotsToSave = new ArrayList<>();
        for(TimeSlot timeSlot : timeSlots){
            if(timeSlot.getStartTime().before(currentTimeStamp)){
                timeSlotRepository.deleteById(timeSlot.getId());
            }
        }
        List<Department> departments = departmentRepository.findAll();
        for(Department department : departments){
            Hibernate.initialize(department.getDoctors());
            Department dep = departmentRepository.findById(department.getId()).get();
            List<TimeSlot> timeslots = createTimeSlotsFromDepartment(department);
            for(TimeSlot timeSlot : timeslots){
                timeSlot.setDepartment(dep);
                Hibernate.initialize(timeSlot.getDepartment().getDoctors());
                List<Doctor> doctors = timeSlot.getDepartment().getDoctors();
                for(Doctor doctor: doctors){
                    timeSlot.addAvailableDoctor(doctor);
                }
            }
            timeSlotsToSave.addAll(timeslots);
        }
        timeSlotRepository.saveAll(timeSlotsToSave);
    }

    @Transactional
    public Timestamp roundTheTimeSlot(Timestamp timestamp){
        LocalDateTime now = timestamp.toLocalDateTime();
        LocalDateTime roundedDateTime;

        if (now.getMinute() >= 30) {
            // Already in the second half of the hour, round to the next hour
            roundedDateTime = now.plusHours(1).truncatedTo(ChronoUnit.HOURS);
        } else {
            // Round to the next 30-minute increment
            roundedDateTime = now.plusMinutes(30 - (now.getMinute() % 30)).truncatedTo(ChronoUnit.MINUTES);
        }

        roundedDateTime = roundedDateTime.withSecond(0); // Truncate seconds to zero

        Timestamp roundedTimestamp = Timestamp.valueOf(roundedDateTime);

        return roundedTimestamp;
    }

}
