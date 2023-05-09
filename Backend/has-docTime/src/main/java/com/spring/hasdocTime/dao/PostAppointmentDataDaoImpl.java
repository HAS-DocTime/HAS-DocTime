package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.entity.PostAppointmentData;
import com.spring.hasdocTime.entity.TimeSlot;
import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.PostAppointmentDataInterface;
import com.spring.hasdocTime.repository.DoctorRepository;
import com.spring.hasdocTime.repository.PostAppointmentDataRepository;
import com.spring.hasdocTime.repository.TimeSlotRepository;
import com.spring.hasdocTime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostAppointmentDataDaoImpl implements PostAppointmentDataInterface {

    private PostAppointmentDataRepository postAppointmentDataRepository;
    private UserRepository userRepository;
    private DoctorRepository doctorRepository;
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    public PostAppointmentDataDaoImpl(
            PostAppointmentDataRepository postAppointmentDataRepository,
            UserRepository userRepository,
            DoctorRepository doctorRepository,
            TimeSlotRepository timeSlotRepository
    ){
        this.postAppointmentDataRepository = postAppointmentDataRepository;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    @Override
    public List<PostAppointmentData> getAllPostAppointmentData() {
        List<PostAppointmentData> allPostAppointmentData= postAppointmentDataRepository.findAll();
        return allPostAppointmentData;

    }

    @Override
    public PostAppointmentData getPostAppointmentDataById(int id) throws DoesNotExistException {
        Optional<PostAppointmentData> optionalPostAppointmentData = postAppointmentDataRepository.findById(id);

        if(optionalPostAppointmentData.isPresent()){
            return optionalPostAppointmentData.get();
        }
        throw new DoesNotExistException("Post Appointment Data");
    }

    @Override
    public List<PostAppointmentData> getPostAppointmentDataByEmail(String email) {
        List<PostAppointmentData> allPostAppointmentData= postAppointmentDataRepository.findByUserEmail(email);
        if(allPostAppointmentData.isEmpty()){
            throw new RuntimeException("PostAppointmentData not found for email" + email);
        }
        return allPostAppointmentData;
    }

    @Override
    public PostAppointmentData createPostAppointmentData(PostAppointmentData postAppointmentData) throws MissingParameterException{
        if(postAppointmentData.getUser()==null){
            throw new MissingParameterException("User");
        }
        if(postAppointmentData.getUser().getId() != 0){
            User user = userRepository.findById(postAppointmentData.getUser().getId()).get();
            postAppointmentData.setUser(user);
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
        User user = userRepository.findById(postAppointmentData.getUser().getId()).get();
        postAppointmentData.setUser(user);
        if(postAppointmentData.getDoctor().getId() != 0){
            Doctor doctor = doctorRepository.findById(postAppointmentData.getDoctor().getId()).get();
            postAppointmentData.setDoctor(doctor);
        }
        if(postAppointmentData.getTimeSlotForAppointmentData().getId() != 0){
            TimeSlot timeSlot = timeSlotRepository.findById(postAppointmentData.getTimeSlotForAppointmentData().getId()).get();
            postAppointmentData.setTimeSlotForAppointmentData(timeSlot);
        }
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
            User user = userRepository.findById(postAppointmentData.getUser().getId()).get();
            postAppointmentData.setUser(user);
            Doctor doctor = doctorRepository.findById(postAppointmentData.getDoctor().getId()).get();
            postAppointmentData.setDoctor(doctor);
            TimeSlot timeSlot = timeSlotRepository.findById(postAppointmentData.getTimeSlotForAppointmentData().getId()).get();
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
}
