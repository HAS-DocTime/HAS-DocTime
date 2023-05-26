package com.spring.hasdocTime.dao;


import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.PatientChronicIllnessInterface;
import com.spring.hasdocTime.interfc.UserInterface;
import com.spring.hasdocTime.repository.ChronicIllnessRepository;
import com.spring.hasdocTime.repository.SymptomRepository;
import com.spring.hasdocTime.repository.UserRepository;
import com.spring.hasdocTime.utills.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDaoImpl implements UserInterface {

    private final UserRepository userRepository;
    private final PatientChronicIllnessInterface patientChronicIllnessDao;
    private final ChronicIllnessRepository chronicIllnessRepository;
    private final SymptomRepository symptomRepository;
    private final PasswordEncoder passwordEncoder;

//    @Override
//    public Page<User> getAllUser(int page, int size, String sortBy, String search) {
//        List<User> userList =  userRepository.findAll();
//        List<User> userListPage  = sortAndSearch(userList, page, size, sortBy, search);
//        Pageable pageable = PageRequest.of(page, size);
//        return new PageImpl<>(userListPage, pageable, userList.size());
//    }

    @Override
    public Page<User> getAllUser(int page, int size, String sortBy, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<User> userList;
        if(search != null && !search.isEmpty()){
            userList = userRepository.findAllAndNameContainsIgnoreCase(search, pageable);
        } else {
            userList =  userRepository.findAll(pageable);
        }
        return userList;
    }

    @Override
    public User getUser(int id) throws DoesNotExistException{
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        }
        throw new DoesNotExistException("User");
    }

    public User updateUserWithPassword(User user) throws DoesNotExistException, MissingParameterException{
        if(user.getName() == null || user.getName().equals("")){
            throw new MissingParameterException("Name");
        }
        if(user.getDob() == null){
            throw new MissingParameterException("Date of Birth");
        }
        if(user.getAge() == 0){
            throw new MissingParameterException("Age");
        }
        if(user.getBloodGroup()==null){
            throw new MissingParameterException("Blood Group");
        }
        if(user.getGender()==null){
            throw new MissingParameterException("Gender");
        }
        if(user.getContact()==null){
            throw new MissingParameterException("Contact");
        }
        if(user.getEmail()==null){
            throw new MissingParameterException("Email");
        }
        if(user.getPassword()==null) {
            throw new MissingParameterException("Password");
        }
        List<Symptom> symptomList = user.getSymptoms();
        if(symptomList!=null){
            List<Symptom> newSymptomList = new ArrayList<>();
            for(Symptom symptom : symptomList) {
                Optional<Symptom> optionalSymptom = symptomRepository.findById(symptom.getId());
                if(optionalSymptom.isEmpty()){
                    throw new DoesNotExistException("Symptom");
                }
                Symptom symptom1 = optionalSymptom.get();
                newSymptomList.add(symptom1);
            }
            user.setSymptoms(newSymptomList);
        }
        List<PatientChronicIllness> patientChronicIllnessList = user.getPatientChronicIllness();
        if(patientChronicIllnessList != null){
            for(PatientChronicIllness patientChronicIllness : patientChronicIllnessList){
                patientChronicIllness.setUser(user);
                CompositeKeyPatientChronicIllness compositeKey;
                Optional<ChronicIllness> optionalChronicIllness = chronicIllnessRepository.findById(patientChronicIllness.getChronicIllness().getId());
                if(optionalChronicIllness.isEmpty()){
                    throw new DoesNotExistException("Chronic Illness");
                }
                ChronicIllness chronicIllness = optionalChronicIllness.get();
                patientChronicIllness.setChronicIllness(chronicIllness);
                compositeKey = new CompositeKeyPatientChronicIllness(user.getId(), patientChronicIllness.getChronicIllness().getId());
                patientChronicIllness.setId(compositeKey);
                user.setPatientChronicIllness(patientChronicIllnessList);
            }
        }
        return userRepository.save(user);
    }



    @Override
    public User createUser(User user) throws MissingParameterException, DoesNotExistException{
        if(user.getName() == null || user.getName().equals("")){
            throw new MissingParameterException("Name");
        }
        if(user.getDob() == null){
            throw new MissingParameterException("Date of Birth");
        }
        if(user.getAge() == 0){
            throw new MissingParameterException("Age");
        }
        if(user.getBloodGroup()==null){
            throw new MissingParameterException("Blood Group");
        }
        if(user.getGender()==null){
            throw new MissingParameterException("Gender");
        }
        if(user.getContact()==null){
            throw new MissingParameterException("Contact");
        }
        if(user.getEmail()==null){
            throw new MissingParameterException("Email");
        }
        if(user.getPassword()==null){
            throw new MissingParameterException("Password");
        }
        if(user.getRole()==null){
            user.setRole(Role.PATIENT);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return updateUserWithPassword(user);
    }

    @Override
    public User updateUser(int id, User user) throws DoesNotExistException, MissingParameterException{
        Optional<User> oldUser = userRepository.findById(id);
        if(oldUser.isPresent()) {
            User oldUserObj = oldUser.get();
            user.setId(oldUserObj.getId());
            for(PatientChronicIllness patientChronicIllness : oldUser.get().getPatientChronicIllness()) {
                patientChronicIllnessDao.deletePatientChronicIllness(patientChronicIllness.getId());
            }
            user.setPassword(oldUserObj.getPassword());
            return updateUserWithPassword(user);
        }
        throw new DoesNotExistException("User");
    }

    @Override
    @Transactional
    public User deleteUser(int id) throws DoesNotExistException{
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            userRepository.deleteById(optionalUser.get().getId());
            return optionalUser.get();
        }
        throw new DoesNotExistException("User");
    }

    @Override
    public User getUserByEmail(String email) throws DoesNotExistException{
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new DoesNotExistException("User");
        }
        return user.get();
    }

    @Override
    public Page<User> getPatients(int page, int size, String sortBy, String search) {
        Page<User> userList;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        if (search != null && !search.isEmpty()){
            userList = userRepository.getPatientsAndNameContainsIgnoreCase(search, pageable);
        }else{
            userList = userRepository.getPatients(pageable);
        }
        return userList;
    }

//    @Override
//    public Page<User> getPatientsByChronicIllnessId(int id, int page, int size, String sortBy, String search) throws DoesNotExistException {
//        Optional<ChronicIllness> optionalChronicIllness = chronicIllnessRepository.findById(id);
//        if (optionalChronicIllness.isEmpty()) {
//            throw new DoesNotExistException("Chronic Illness");
//        } else {
//            Set<User> users = new HashSet<>();
//            for (int i = 0; i < optionalChronicIllness.get().getPatientChronicIllnesses().toArray().length; i++) {
//                Integer tempUserId = (optionalChronicIllness.get().getPatientChronicIllnesses().get(i).getId().getPatientId());
//                Optional<User> tempUser = userRepository.findById(tempUserId);
//                if (tempUser.isPresent()) {
//                    users.add(tempUser.get());
//                } else {
//                    return null;
//                }
//            }
//            List<User> userList = new ArrayList<>(users);
//            List<User> userListPage  = sortAndSearch(userList, page, size, sortBy, search);
//            Pageable pageable = PageRequest.of(page, size);
//            return new PageImpl<>(userListPage, pageable, userList.size());
//        }
//    }


    @Override
    public Page<User> getPatientsByChronicIllnessId(int id, int page, int size, String sortBy, String search) throws DoesNotExistException {
        Optional<ChronicIllness> optionalChronicIllness = chronicIllnessRepository.findById(id);
        if (optionalChronicIllness.isEmpty()) {
            throw new DoesNotExistException("Chronic Illness");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        // Use the appropriate query method in your userRepository to fetch the users with pagination, sorting, and searching
        Page<User> userPage;
        if (search != null && !search.isEmpty()) {
            //To search based on user's name only
            userPage = userRepository.findByChronicIllnessIdAndNameContainsIgnoreCase(id, search, pageable);
        } else {
            userPage = userRepository.findByChronicIllnessId(id, pageable);
        }

        return userPage;
    }


//    private Object getField(User user, String fieldName) {
//        try {
//            Field field = User.class.getDeclaredField(fieldName);
//            field.setAccessible(true);
//            return field.get(user);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            // Handle exceptions accordingly
//            throw new IllegalArgumentException("Invalid field name: " + fieldName);
//        }
//    }
//
//    private List<User> sortAndSearch(List<User> users, int page, int size, String sortBy, String search){
//
//        // Apply sorting
//        Comparator<User> comparator = Comparator.comparing(user -> (Comparable) getField(user, sortBy));
//        users.sort(comparator);
//
//        // Apply searching
//        if (search != null && !search.isEmpty()) {
//            users = users.stream()
//                    .filter(user -> user.getName().contains(search))
//                    .collect(Collectors.toList());
//        }
//
//        // Create pageable object
//        int startIndex = page * size;
//        int endIndex = Math.min(startIndex + size, users.size());
//
//        // Create a sublist based on the page and size
//        List<User> userListPage = users.subList(startIndex, endIndex);
//
//        return userListPage;
//    }

}
