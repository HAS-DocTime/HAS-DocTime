package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.PostAppointmentDataInterface;
import com.spring.hasdocTime.interfaces.UserInterface;
import com.spring.hasdocTime.repository.DoctorRepository;
import com.spring.hasdocTime.repository.PostAppointmentDataRepository;
import com.spring.hasdocTime.repository.TimeSlotRepository;
import com.spring.hasdocTime.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of the PostAppointmentDataInterface that provides CRUD operations for post-appointment data.
 */
@Service
public class PostAppointmentDataDaoImpl implements PostAppointmentDataInterface {

    private PostAppointmentDataRepository postAppointmentDataRepository;
    private UserRepository userRepository;
    private DoctorRepository doctorRepository;
    private TimeSlotRepository timeSlotRepository;
    private UserInterface userDao;

    @Autowired
    public PostAppointmentDataDaoImpl(
            PostAppointmentDataRepository postAppointmentDataRepository,
            UserRepository userRepository,
            DoctorRepository doctorRepository,
            TimeSlotRepository timeSlotRepository,
            @Qualifier("userDaoImpl") UserInterface userInterface
    ){
        this.postAppointmentDataRepository = postAppointmentDataRepository;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.userDao = userInterface;
    }

    /**
     * Retrieves all post-appointment data.
     *
     * @return a list of post-appointment data
     */
    @Override
    public List<PostAppointmentData> getAllPostAppointmentData() {
        List<PostAppointmentData> postAppointmentDataList =  postAppointmentDataRepository.findAll();
        for(PostAppointmentData postAppointmentData : postAppointmentDataList){
            Hibernate.initialize(postAppointmentData.getUser());
            Hibernate.initialize(postAppointmentData.getDoctor().getUser());
            Hibernate.initialize(postAppointmentData.getTimeSlotForAppointmentData());
        }
        return postAppointmentDataList;
    }

    /**
     * Retrieves a specific post-appointment data by its ID.
     *
     * @param id the ID of the post-appointment data
     * @return the post-appointment data with the specified ID
     * @throws DoesNotExistException if the post-appointment data does not exist
     */
    @Override
    public PostAppointmentData getPostAppointmentDataById(int id) throws DoesNotExistException {
        Optional<PostAppointmentData> optionalPostAppointmentData = postAppointmentDataRepository.findById(id);

        if(optionalPostAppointmentData.isPresent()){
            Hibernate.initialize(optionalPostAppointmentData.get().getDoctor().getUser());
            Hibernate.initialize(optionalPostAppointmentData.get().getDoctor().getDepartment());
            Hibernate.initialize(optionalPostAppointmentData.get().getTimeSlotForAppointmentData());
            Hibernate.initialize(optionalPostAppointmentData.get().getUser());
            for(PatientChronicIllness pc : optionalPostAppointmentData.get().getUser().getPatientChronicIllness()){
                Hibernate.initialize(pc.getChronicIllness());
            }
            Hibernate.initialize(optionalPostAppointmentData.get().getSymptoms());
            return optionalPostAppointmentData.get();
        }
        throw new DoesNotExistException("Post Appointment Data");
    }

    /**
     * Retrieves all post-appointment data associated with a user's email.
     *
     * @param email the email of the user
     * @return a list of post-appointment data associated with the user's email
     */
    @Override
    public List<PostAppointmentData> getPostAppointmentDataByEmail(String email) {
        List<PostAppointmentData> allPostAppointmentData= postAppointmentDataRepository.findByUserEmail(email);
        for(PostAppointmentData postAppointmentData : allPostAppointmentData){
            Hibernate.initialize(postAppointmentData.getTimeSlotForAppointmentData());
            Hibernate.initialize(postAppointmentData.getDoctor().getUser());
        }
        return allPostAppointmentData;
    }

    /**
     * Creates a new post-appointment data.
     *
     * @param postAppointmentData the post-appointment data to create
     * @return the created post-appointment data
     * @throws MissingParameterException if any required parameters are missing
     * @throws DoesNotExistException    if the associated user, doctor, or time slot does not exist
     */
    @Override
    public PostAppointmentData createPostAppointmentData(PostAppointmentData postAppointmentData) throws MissingParameterException, DoesNotExistException {
        // Check for missing parameters
        if(postAppointmentData.getUser()==null){
            throw new MissingParameterException("User");
        }
        if(postAppointmentData.getUser().getId()==0){
            throw new MissingParameterException("UserId");
        }
        if(postAppointmentData.getDoctor()==null){
            throw new MissingParameterException("Doctor");
        }
        if(postAppointmentData.getDoctor().getId()==0){
            throw new MissingParameterException("DoctorId");
        }
        if(postAppointmentData.getTimeSlotForAppointmentData()==null){
            throw new MissingParameterException("Time Slot");
        }
        if(postAppointmentData.getTimeSlotForAppointmentData().getId()==0) {
            throw new MissingParameterException("Time Slot Id");
        }

        // Check if the associated user exists
        Optional<User> optionalUser = userRepository.findById(postAppointmentData.getUser().getId());
        if(optionalUser.isEmpty()){
            throw new DoesNotExistException("User");
        }
        User user = optionalUser.get();
        postAppointmentData.setUser(user);

        // Check if the associated doctor exists
        Optional<Doctor> optionalDoctor = doctorRepository.findById(postAppointmentData.getDoctor().getId());
        if(optionalDoctor.isEmpty()){
            throw new DoesNotExistException("Doctor");
        }
        Doctor doctor = optionalDoctor.get();
        postAppointmentData.setDoctor(doctor);

        // Check if the associated time slot exists
        Optional<TimeSlot> optionalTimeSlot = timeSlotRepository.findById(postAppointmentData.getTimeSlotForAppointmentData().getId());
        if(optionalTimeSlot.isEmpty()){
            throw new DoesNotExistException("Time Slot");
        }
        TimeSlot timeSlot = optionalTimeSlot.get();
        postAppointmentData.setTimeSlotForAppointmentData(timeSlot);

        return postAppointmentDataRepository.save(postAppointmentData);
    }

    /**
     * Updates an existing post-appointment data.
     *
     * @param id                the ID of the post-appointment data to update
     * @param postAppointmentData the updated post-appointment data
     * @return the updated post-appointment data
     * @throws DoesNotExistException    if the post-appointment data or associated user, doctor, or time slot does not exist
     * @throws MissingParameterException if any required parameters are missing
     */
    @Override
    public PostAppointmentData updatePostAppointmentData(int id, PostAppointmentData postAppointmentData) throws DoesNotExistException, MissingParameterException {
        // Check for missing parameters
        if(postAppointmentData.getUser()==null){
            throw new MissingParameterException("User");
        }
        if(postAppointmentData.getUser().getId()==0){
            throw new MissingParameterException("UserId");
        }
        if(postAppointmentData.getDoctor()==null){
            throw new MissingParameterException("Doctor");
        }
        if(postAppointmentData.getDoctor().getId()==0){
            throw new MissingParameterException("DoctorId");
        }
        if(postAppointmentData.getTimeSlotForAppointmentData()==null){
            throw new MissingParameterException("Time Slot");
        }
        if(postAppointmentData.getTimeSlotForAppointmentData().getId()==0) {
            throw new MissingParameterException("Time Slot Id");
        }

        // Check if the post-appointment data exists
        Optional<PostAppointmentData> oldPostAppointment = postAppointmentDataRepository.findById(id);
        if(oldPostAppointment.isPresent()){
            PostAppointmentData oldPostAppointmentDataObj = oldPostAppointment.get();
            postAppointmentData.setId(id);

            // Check if the associated user exists
            Optional<User> optionalUser = userRepository.findById(postAppointmentData.getUser().getId());
            if(optionalUser.isEmpty()){
                throw new DoesNotExistException("User");
            }
            User user = optionalUser.get();
            postAppointmentData.setUser(user);

            // Check if the associated doctor exists
            Optional<Doctor> optionalDoctor = doctorRepository.findById(postAppointmentData.getDoctor().getId());
            if(optionalDoctor.isEmpty()){
                throw new DoesNotExistException("Doctor");
            }
            Doctor doctor = optionalDoctor.get();
            postAppointmentData.setDoctor(doctor);

            // Check if the associated time slot exists
            Optional<TimeSlot> optionalTimeSlot = timeSlotRepository.findById(postAppointmentData.getTimeSlotForAppointmentData().getId());
            if(optionalTimeSlot.isEmpty()){
                throw new DoesNotExistException("Time Slot");
            }
            TimeSlot timeSlot = optionalTimeSlot.get();
            postAppointmentData.setTimeSlotForAppointmentData(timeSlot);

            return postAppointmentDataRepository.save(postAppointmentData);
        }
        throw new DoesNotExistException("Post Appointment Data");
    }

    /**
     * Deletes a specific post-appointment data by its ID.
     *
     * @param id the ID of the post-appointment data to delete
     * @return a message indicating the success of the deletion
     * @throws DoesNotExistException if the post-appointment data does not exist
     */
    @Override
    public String deletePostAppointmentData(int id) throws DoesNotExistException {
        Optional<PostAppointmentData> optionalPostAppointmentData = postAppointmentDataRepository.findById(id);
        if(optionalPostAppointmentData.isPresent()){
            postAppointmentDataRepository.deleteById(id);
            return "postAppointmentData with id: " + id + " is deleted";
        }
        throw new DoesNotExistException("Post Appointment Data");
    }

    /**
     * Retrieves diseases grouped by symptom.
     *
     * @param symptom the symptom
     * @return a list of maps containing the diseases grouped by symptom
     * @throws DoesNotExistException if no post-appointment data exists for the specified symptom
     */
    @Override
    public List<Map<String, Integer>> getDiseasesGroupedBySymptom(String symptom) throws DoesNotExistException {
        return postAppointmentDataRepository.findDiseasesGroupedBySymptom(symptom);
    }

    /**
     * Retrieves post-appointment data for a specific symptom.
     *
     * @param symptom the symptom
     * @return a list of post-appointment data for the specified symptom
     * @throws DoesNotExistException if no post-appointment data exists for the specified symptom
     */
    @Override
    public List<PostAppointmentData> getPostAppointmentDataBySymptom(String symptom) throws DoesNotExistException {
        List<PostAppointmentData> postAppointmentDataList = postAppointmentDataRepository.findPostAppointmentDataGroupedBySymptom(symptom);
        for(PostAppointmentData postAppointmentData : postAppointmentDataList){
            Hibernate.initialize(postAppointmentData.getUser());
            Hibernate.initialize(postAppointmentData.getDoctor().getUser());
            Hibernate.initialize(postAppointmentData.getTimeSlotForAppointmentData());
        }
        return postAppointmentDataList;
    }

    /**
     * Retrieves post-appointment data associated with a specific doctor.
     *
     * @param id the ID of the doctor
     * @return a list of post-appointment data associated with the specified doctor
     * @throws DoesNotExistException if the doctor does not exist
     */
    public List<PostAppointmentData> getPostAppointmentsDataOfDoctor(int id) throws DoesNotExistException {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if(optionalDoctor.isEmpty()){
            throw new DoesNotExistException("Doctor");
        }
        Doctor doctor = optionalDoctor.get();
        for(PostAppointmentData postAppointmentData : doctor.getPostAppointmentData()){
            Hibernate.initialize(postAppointmentData.getUser());
            Hibernate.initialize(postAppointmentData.getTimeSlotForAppointmentData());
        }
        return doctor.getPostAppointmentData();
    }

    /**
     * Retrieves post-appointment data associated with a specific user.
     *
     * @param id the ID of the user
     * @return a list of post-appointment data associated with the specified user
     * @throws DoesNotExistException if the user does not exist
     */
    @Override
    public List<PostAppointmentData> getPostAppointmentDataByUserId(int id) throws DoesNotExistException {
        User user = userDao.getUser(id);
        List<PostAppointmentData> postAppointmentDataList = user.getAppointmentData();
        for(PostAppointmentData postAppointmentData : postAppointmentDataList){
            Hibernate.initialize(postAppointmentData.getTimeSlotForAppointmentData());
            Hibernate.initialize(postAppointmentData.getDoctor().getUser());
            Hibernate.initialize(postAppointmentData.getDoctor().getDepartment());
        }
        return postAppointmentDataList;
    }
}
