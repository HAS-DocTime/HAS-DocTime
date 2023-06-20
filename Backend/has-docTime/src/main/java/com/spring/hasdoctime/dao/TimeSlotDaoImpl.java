package com.spring.hasdoctime.dao;

import com.spring.hasdoctime.constants.Constant;
import com.spring.hasdoctime.entity.*;
import com.spring.hasdoctime.exceptionhandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionhandling.exception.MissingParameterException;
import com.spring.hasdoctime.interfaces.TimeSlotInterface;
import com.spring.hasdoctime.repository.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Implementation of the TimeSlotInterface that interacts with the TimeSlotRepository to perform CRUD operations on TimeSlot entities.
 */
@Service
public class TimeSlotDaoImpl implements TimeSlotInterface {

    LocalDateTime localDateTimeForHospitalStartTime = LocalDateTime.of(1970, 1, 1, 9, 0, 0);
    LocalDateTime localDateTimeForHospitalEndTime = LocalDateTime.of(2050, 12,31,21,0,0,0);

    private final Timestamp hospitalStartTime = Timestamp.valueOf(localDateTimeForHospitalStartTime);
    private final Timestamp hospitalEndTime = Timestamp.valueOf(localDateTimeForHospitalEndTime);

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
        return timeSlotRepository.findAll();
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
        throw new DoesNotExistException(Constant.TIME_SLOT);
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
        validateRequiredFields(timeSlot);
        Optional<Department> optionalDepartment = departmentRepository.findById(timeSlot.getDepartment().getId());
        if(optionalDepartment.isEmpty()){
            throw new DoesNotExistException(Constant.DEPARTMENT);
        }
        Department department = optionalDepartment.get();
        timeSlot.setDepartment(department);
        if(timeSlot.getAppointment() != null){
            Set<Appointment> appointmentList = new HashSet<>();
            timeSlot.setAppointment(getSetOfAppointmentsWithWholeObject(timeSlot, appointmentList));

        }
        if(timeSlot.getAppointmentData() != null){
            Set<PostAppointmentData> postAppointmentDataList = new HashSet<>();
            timeSlot.setAppointmentData(getListOfPostAppointmentDataWithWholeObjects(timeSlot, postAppointmentDataList));
        }

        Set<Doctor> availableDoctors = new HashSet<>();
        if(timeSlot.getAvailableDoctors() != null){
            timeSlot.setAvailableDoctors(getSetOfAvailableDoctorsWithWholeObjects(timeSlot, availableDoctors));
        }

        Set<Doctor> bookedDoctors = new HashSet<>();
        if(timeSlot.getBookedDoctors() != null){
            timeSlot.setAvailableDoctors(getSetOfBookedDoctorsWithWholeObjects(timeSlot, bookedDoctors));
        }
        return timeSlotRepository.save(timeSlot);
    }

    private Set<PostAppointmentData> getListOfPostAppointmentDataWithWholeObjects(TimeSlot timeSlot, Set<PostAppointmentData> postAppointmentDataList) throws DoesNotExistException {
        for(PostAppointmentData postAppointmentData : timeSlot.getAppointmentData()) {
            Optional<PostAppointmentData> optionalPostAppointmentData = postAppointmentDataRepository.findById(postAppointmentData.getId());
            if (optionalPostAppointmentData.isEmpty()) {
                throw new DoesNotExistException("Post Appointment");
            }
            PostAppointmentData foundPostAppointmentData = optionalPostAppointmentData.get();
            postAppointmentDataList.add(foundPostAppointmentData);
        }
        return postAppointmentDataList;
    }

    private Set<Doctor> getSetOfAvailableDoctorsWithWholeObjects(TimeSlot timeSlot, Set<Doctor> availableDoctors) throws DoesNotExistException {
        for(Doctor d: timeSlot.getAvailableDoctors()){
            if(d.getId() != 0){
                Optional<Doctor> optionalAvailableDoctor = doctorRepository.findById(d.getId());
                if(optionalAvailableDoctor.isEmpty()){
                    throw new DoesNotExistException("Doctor");
                }
                Doctor doctor = optionalAvailableDoctor.get();
                availableDoctors.add(doctor);
            }
        }
        return availableDoctors;
    }

    private Set<Doctor> getSetOfBookedDoctorsWithWholeObjects(TimeSlot timeSlot, Set<Doctor> bookedDoctors) throws DoesNotExistException {
        for(Doctor d: timeSlot.getBookedDoctors()){
            if(d.getId() != 0){
                Optional<Doctor> optionalBookedDoctor = doctorRepository.findById(d.getId());
                if(optionalBookedDoctor.isEmpty()){
                    throw new DoesNotExistException("Doctor");
                }
                Doctor doctor = optionalBookedDoctor.get();
                bookedDoctors.add(doctor);
            }
        }
        return bookedDoctors;
    }

    private static void validateRequiredFields(TimeSlot timeSlot) throws MissingParameterException {
        if(timeSlot.getStartTime()==null){
            throw new MissingParameterException("Start Time");
        }
        if(timeSlot.getEndTime()==null){
            throw new MissingParameterException("End Time");
        }
        if(timeSlot.getDepartment()==null){
            throw new MissingParameterException(Constant.DEPARTMENT);
        }
        if(timeSlot.getDepartment().getId()==0){
            throw new MissingParameterException("DepartmentId");
        }
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
        validateRequiredFields(timeSlot);
        Optional<TimeSlot> oldTimeSlot = timeSlotRepository.findById(id);
        if(oldTimeSlot.isPresent()) {
            timeSlot.setId(id);
            if(timeSlot.getDepartment() != null){
                Optional<Department> optionalDepartment = departmentRepository.findById(timeSlot.getDepartment().getId());
                if(optionalDepartment.isEmpty()){
                    throw new DoesNotExistException(Constant.DEPARTMENT);
                }
                Department department = optionalDepartment.get();
                timeSlot.setDepartment(department);
            }

            if(timeSlot.getAppointment() != null){
                Set<Appointment> appointments = new HashSet<>();
                timeSlot.setAppointment(getSetOfAppointmentsWithWholeObject(timeSlot, appointments));
            }

            if(timeSlot.getAppointmentData() != null){
                Set<PostAppointmentData> postAppointmentDataList = new HashSet<>();
                timeSlot.setAppointmentData(getListOfPostAppointmentDataWithWholeObjects(timeSlot, postAppointmentDataList));
            }
            Set<Doctor> availableDoctors = new HashSet<>();
            if(timeSlot.getAvailableDoctors() != null){
                timeSlot.setAvailableDoctors(getSetOfAvailableDoctorsWithWholeObjects(timeSlot, availableDoctors));
            }

            Set<Doctor> bookedDoctors = new HashSet<>();
            if(timeSlot.getBookedDoctors() != null){
                timeSlot.setBookedDoctors(getSetOfBookedDoctorsWithWholeObjects(timeSlot, bookedDoctors));
            }
            timeSlotRepository.save(timeSlot);
            return timeSlot;
        }
        throw new DoesNotExistException(Constant.TIME_SLOT);
    }

    private Set<Appointment> getSetOfAppointmentsWithWholeObject(TimeSlot timeSlot, Set<Appointment> appointments) throws DoesNotExistException {
        for(Appointment appointment : timeSlot.getAppointment()){
            Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointment.getId());
            if(optionalAppointment.isEmpty()){
                throw new DoesNotExistException("Appointment");
            }
            Appointment foundAppointment = optionalAppointment.get();
            appointments.add(foundAppointment);
        }
        return appointments;
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
        throw new DoesNotExistException(Constant.TIME_SLOT);
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

        LocalDateTime localDateTimeForTimeSlotStartTime = timeSlotStartTime.toLocalDateTime();

        Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
        LocalDateTime localDateTimeForCurrentTimeStamp = currentTimeStamp.toLocalDateTime();
        localDateTimeForTimeSlotStartTime = localDateTimeForTimeSlotStartTime.withYear(localDateTimeForCurrentTimeStamp.getYear());
        localDateTimeForTimeSlotStartTime = localDateTimeForTimeSlotStartTime.withMonth(localDateTimeForCurrentTimeStamp.getMonthValue());
        localDateTimeForTimeSlotStartTime = localDateTimeForTimeSlotStartTime.withDayOfMonth(localDateTimeForCurrentTimeStamp.getDayOfMonth()+index);
        timeSlotStartTime = Timestamp.valueOf(localDateTimeForTimeSlotStartTime);
        int timeDuration = department.getTimeDuration();
        Timestamp timeSlotEndTime = (Timestamp) timeSlotStartTime.clone();
        LocalDateTime localDateTimeForTimeSlotEndTime = timeSlotEndTime.toLocalDateTime();
        localDateTimeForTimeSlotEndTime = localDateTimeForTimeSlotEndTime.withMinute(localDateTimeForTimeSlotEndTime.getMinute()+timeDuration);
        timeSlotEndTime = Timestamp.valueOf(localDateTimeForTimeSlotEndTime);
        Timestamp tentativeEndTime = (Timestamp) hospitalEndTime.clone();
        LocalDateTime localDateTimeForTentativeEndTime = tentativeEndTime.toLocalDateTime();
        localDateTimeForTentativeEndTime = localDateTimeForTentativeEndTime.withYear(localDateTimeForCurrentTimeStamp.getYear());
        localDateTimeForTentativeEndTime = localDateTimeForTentativeEndTime.withMonth(localDateTimeForCurrentTimeStamp.getMonthValue());
        localDateTimeForTentativeEndTime = localDateTimeForTentativeEndTime.withDayOfMonth(localDateTimeForCurrentTimeStamp.getDayOfMonth()+index);
        tentativeEndTime = Timestamp.valueOf(localDateTimeForTentativeEndTime);
        int timeLimit = timeSlotEndTime.compareTo(tentativeEndTime);
        while(timeLimit<=0){
            TimeSlot timeSlot = new TimeSlot();
            timeSlot.setStartTime(timeSlotStartTime);
            timeSlot.setEndTime(timeSlotEndTime);
            timeSlot.setDepartment(department);
            TimeSlot checkTimeSlot = timeSlotRepository.checkIfTimeSlotExists(timeSlot.getStartTime(), timeSlot.getEndTime(), timeSlot.getDepartment().getId());
            if(checkTimeSlot == null){
                timeSlots.add(timeSlot);
            }
            timeSlotStartTime = (Timestamp) timeSlotEndTime.clone();
            timeSlotEndTime = (Timestamp) timeSlotStartTime.clone();
            localDateTimeForTimeSlotEndTime = timeSlotEndTime.toLocalDateTime();
            localDateTimeForTimeSlotEndTime = localDateTimeForTimeSlotEndTime.withMinute(localDateTimeForTimeSlotEndTime.getMinute()+timeDuration);
            timeSlotEndTime = Timestamp.valueOf(localDateTimeForTimeSlotEndTime);
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
        return timeSlots;
    }

    @Transactional
    @Scheduled(cron = "20 50 16 ? * *", zone = "Asia/Kolkata") // For Testing
//    @Scheduled(cron = "0 0 0 ? * *", zone = "Asia/Kolkata")
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
            roundedDateTime = now.plusMinutes((long)30 - (now.getMinute() % 30)).truncatedTo(ChronoUnit.MINUTES);
        }

        roundedDateTime = roundedDateTime.withSecond(0); // Truncate seconds to zero

        return Timestamp.valueOf(roundedDateTime);
    }

}
