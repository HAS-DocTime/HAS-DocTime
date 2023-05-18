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
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDaoImpl implements UserInterface {

    private final UserRepository userRepository;
    private final PatientChronicIllnessInterface patientChronicIllnessDao;
    private final ChronicIllnessRepository chronicIllnessRepository;
    private final SymptomRepository symptomRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
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
//                compositeKey = new CompositeKeyPatientChronicIllness(user.getId(), patientChronicIllness.getChronicIllness().getId());
//                patientChronicIllness.setId(compositeKey);
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
    public User deleteUser(int id) throws DoesNotExistException{
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
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
    
    
}
