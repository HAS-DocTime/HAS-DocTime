package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.entity.PostAppointmentData;
import com.spring.hasdocTime.entity.TimeSlot;
import com.spring.hasdocTime.entity.User;
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
    public PostAppointmentData getPostAppointmentDataById(int id) {
        Optional<PostAppointmentData> optionalPostAppointmentData = postAppointmentDataRepository.findById(id);

        if(optionalPostAppointmentData.isPresent()){
            return optionalPostAppointmentData.get();
        }else{
            throw new RuntimeException("PostAppointmentData not found for id" + id);
        }
    }

    @Override
    public PostAppointmentData createPostAppointmentData(PostAppointmentData postAppointmentData) {
        if(postAppointmentData.getUser().getId() != 0){
            User user = userRepository.findById(postAppointmentData.getUser().getId()).get();
            postAppointmentData.setUser(user);
        }
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
    public PostAppointmentData updatePostAppointmentData(int id, PostAppointmentData postAppointmentData) {
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
        return null;
//        PostAppointmentData existingPostAppointmentData = getPostAppointmentDataById(id);
//        existingPostAppointmentData.setDisease(postAppointmentData.getDisease());
//        existingPostAppointmentData.setMedicine(postAppointmentData.getMedicine());
//        existingPostAppointmentData.setUser(postAppointmentData.getUser());
//        existingPostAppointmentData.setDoctor(postAppointmentData.getDoctor());
//        existingPostAppointmentData.setTimeSlotForAppointmentData(postAppointmentData.getTimeSlotForAppointmentData());
//        return postAppointmentDataRepository.save(existingPostAppointmentData);
    }

    @Override
    public String deletePostAppointmentData(int id) {
        Optional<PostAppointmentData> optionalPostAppointmentData = postAppointmentDataRepository.findById(id);
        if(optionalPostAppointmentData.isPresent()){
            postAppointmentDataRepository.deleteById(id);
            return "postAppointmentData with id: " + id + " is deleted";
        }else{
            return "postAppointmentData with id: " + id + " doesn't exist";
        }
    }
}
