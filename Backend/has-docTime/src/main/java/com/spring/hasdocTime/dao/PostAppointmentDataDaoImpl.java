package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.entity.PostAppointmentData;
import com.spring.hasdocTime.entity.TimeSlot;
import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.PostAppointmentDataInterface;
import com.spring.hasdocTime.interfaces.UserInterface;
import com.spring.hasdocTime.repository.DoctorRepository;
import com.spring.hasdocTime.repository.PostAppointmentDataRepository;
import com.spring.hasdocTime.repository.TimeSlotRepository;
import com.spring.hasdocTime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<PostAppointmentData> getAllPostAppointmentData(int page, int size, String sortBy, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<PostAppointmentData> postAppointmentData;
        if(search != null && !search.isEmpty()){
            //search Appointments based on userName only.
            postAppointmentData = postAppointmentDataRepository.findAllAndUserNameContainsIgnoreCase(search, pageable);
        }
        else{
            postAppointmentData = postAppointmentDataRepository.findAll(pageable);
        }
        return postAppointmentData;
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
    public Page<PostAppointmentData> getPostAppointmentDataByEmail(String email,int page, int size, String sortBy, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<PostAppointmentData> allPostAppointmentData;
        if(search != null && !search.isEmpty()){
            allPostAppointmentData= postAppointmentDataRepository.findByUserEmailAndDiseaseContainsIgnoreCase(email, search, pageable);
        }else {
            allPostAppointmentData= postAppointmentDataRepository.findByUserEmail(email, pageable);
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

//    @Override
//    public List<Map<String, Integer>> getDiseasesGroupedBySymptom(String symptom) throws DoesNotExistException {
//        return postAppointmentDataRepository.findDiseasesGroupedBySymptom(symptom);
//    }



    @Override
    public List<Map<String, Integer>> getDiseaseListGroupedBySymptom(String symptom) throws DoesNotExistException {
        return postAppointmentDataRepository.findDiseaseListGroupedBySymptom(symptom);
    }

//    @Override
//    public List<PostAppointmentData> getPostAppointmentDataBySymptom(String symptom) throws DoesNotExistException {
//        return postAppointmentDataRepository.findPostAppointmentDataGroupedBySymptom(symptom);
//    }

    @Override
    public Page<PostAppointmentData> getPostAppointmentDataBySymptom(String symptom, int page, int size, String sortBy, String search) throws DoesNotExistException {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<PostAppointmentData> postAppointmentData;
        if(search != null && !search.isEmpty()){
            //search Appointments based on userName only.
            postAppointmentData = postAppointmentDataRepository.findPostAppointmentDataGroupedBySymptomAndUserNameContainsIgnoreCase(symptom, search, pageable);
        }
        else{
            postAppointmentData = postAppointmentDataRepository.findPostAppointmentDataGroupedBySymptom(symptom, pageable);
        }
        return postAppointmentData;
    }

//    @Override
//    public List<PostAppointmentData> getPostAppointmentsDataOfDoctor(int id) throws DoesNotExistException{
//        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
//        if(optionalDoctor.isEmpty()){
//            throw new DoesNotExistException("Doctor");
//        }
//        Doctor doctor = optionalDoctor.get();
//        return doctor.getPostAppointmentData();
//    }

    @Override
    public Page<PostAppointmentData> getPostAppointmentsDataOfDoctor(int id, int page, int size, String sortBy, String search) throws DoesNotExistException{
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if(optionalDoctor.isEmpty()){
            throw new DoesNotExistException("Doctor");
        }
        Doctor doctor = optionalDoctor.get();
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<PostAppointmentData> postAppointmentData;
        if(search != null && !search.isEmpty()){
            postAppointmentData = postAppointmentDataRepository.findByDoctorAndUserNameContainsIgnoreCase(doctor.getId(), search, pageable);
        }else {
            postAppointmentData = postAppointmentDataRepository.findByDoctor(doctor.getId(), pageable);
        }
        return postAppointmentData;
    }

//    @Override
//    public List<PostAppointmentData> getPostAppointmentDataByUserId(int id) throws DoesNotExistException {
//        User user = userDao.getUser(id);
//        List<PostAppointmentData> postAppointmentDataList = user.getAppointmentData();
//        return postAppointmentDataList;
//    }

    @Override
    public Page<PostAppointmentData> getPostAppointmentDataByUserId(int id, int page, int size, String sortBy, String search) throws DoesNotExistException {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new DoesNotExistException("User");
        }
        User currentUser = user.get();
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<PostAppointmentData> postAppointmentData;
        if(search != null && !search.isEmpty()){
            postAppointmentData = postAppointmentDataRepository.findByUserAndDoctorNameContainsIgnoreCase(currentUser.getId(), search, pageable);
        }else {
            postAppointmentData = postAppointmentDataRepository.findByUser(currentUser.getId(), pageable);
        }
        return postAppointmentData;
    }
}
