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

    @Override
    public List<PostAppointmentData> getPostAppointmentDataByEmail(String email) {
        List<PostAppointmentData> allPostAppointmentData= postAppointmentDataRepository.findByUserEmail(email);
        for(PostAppointmentData postAppointmentData : allPostAppointmentData){
            Hibernate.initialize(postAppointmentData.getTimeSlotForAppointmentData());
            Hibernate.initialize(postAppointmentData.getDoctor().getUser());
        }
        return allPostAppointmentData;
    }

    @Override
    public PostAppointmentData createPostAppointmentData(PostAppointmentData postAppointmentData) throws MissingParameterException, DoesNotExistException{
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
        Optional<User> optionalUser = userRepository.findById(postAppointmentData.getUser().getId());
        if(optionalUser.isEmpty()){
            throw new DoesNotExistException("User");
        }
        User user = optionalUser.get();
        postAppointmentData.setUser(user);
        Optional<Doctor> optionalDoctor = doctorRepository.findById(postAppointmentData.getDoctor().getId());
        if(optionalDoctor.isEmpty()){
            throw new DoesNotExistException("Doctor");
        }
        Doctor doctor = optionalDoctor.get();
        postAppointmentData.setDoctor(doctor);
        Optional<TimeSlot> optionalTimeSlot = timeSlotRepository.findById(postAppointmentData.getTimeSlotForAppointmentData().getId());
        if(optionalTimeSlot.isEmpty()){
            throw new DoesNotExistException("Time Slot");
        }
        TimeSlot timeSlot = optionalTimeSlot.get();
        postAppointmentData.setTimeSlotForAppointmentData(timeSlot);
        return postAppointmentDataRepository.save(postAppointmentData);
    }

    @Override
    public PostAppointmentData updatePostAppointmentData(int id, PostAppointmentData postAppointmentData) throws DoesNotExistException, MissingParameterException {
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
        Optional<PostAppointmentData> oldPostAppointment = postAppointmentDataRepository.findById(id);
        if(oldPostAppointment.isPresent()){
            PostAppointmentData oldPostAppointmentDataObj = oldPostAppointment.get();
            postAppointmentData.setId(id);
            Optional<User> optionalUser = userRepository.findById(postAppointmentData.getUser().getId());
            if(optionalUser.isEmpty()){
                throw new DoesNotExistException("User");
            }
            User user = optionalUser.get();
            postAppointmentData.setUser(user);

            Optional<Doctor> optionalDoctor = doctorRepository.findById(postAppointmentData.getDoctor().getId());
            if(optionalDoctor.isEmpty()){
                throw new DoesNotExistException("Doctor");
            }
            Doctor doctor = optionalDoctor.get();
            postAppointmentData.setDoctor(doctor);
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

    @Override
    public String deletePostAppointmentData(int id) throws DoesNotExistException{
        Optional<PostAppointmentData> optionalPostAppointmentData = postAppointmentDataRepository.findById(id);
        if(optionalPostAppointmentData.isPresent()){
            postAppointmentDataRepository.deleteById(id);
            return "postAppointmentData with id: " + id + " is deleted";
        }
        throw new DoesNotExistException("Post Appointment Data");
    }

    @Override
    public List<Map<String, Integer>> getDiseasesGroupedBySymptom(String symptom) throws DoesNotExistException {
        return postAppointmentDataRepository.findDiseasesGroupedBySymptom(symptom);
    }

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
    
    public List<PostAppointmentData> getPostAppointmentsDataOfDoctor(int id) throws DoesNotExistException{
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
