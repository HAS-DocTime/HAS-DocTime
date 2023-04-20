package com.spring.hasdocTime.dao;


import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.interfc.UserInterface;
import com.spring.hasdocTime.repository.ChronicIllnessRepository;
import com.spring.hasdocTime.repository.PatientChronicIllnessRepository;
import com.spring.hasdocTime.repository.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.hasdocTime.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDaoImpl implements UserInterface {

    private UserRepository userRepository;

    private PatientChronicIllnessRepository patientChronicIllnessRepository;

    private ChronicIllnessRepository chronicIllnessRepository;

    private SymptomRepository symptomRepository;

    @Autowired
    public UserDaoImpl(UserRepository userRepository, PatientChronicIllnessRepository patientChronicIllnessRepository,ChronicIllnessRepository chronicIllnessRepository, SymptomRepository symptomRepository) {
        this.userRepository = userRepository;
        this.patientChronicIllnessRepository = patientChronicIllnessRepository;
        this.chronicIllnessRepository = chronicIllnessRepository;
        this.symptomRepository = symptomRepository;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        }
        return null;
    }

    @Override
    public User createUser(User user) {
        List<Symptom> symptomList = user.getSymptoms();
        List<Symptom> newSymptomList = new ArrayList<>();
        for(Symptom symptom : symptomList) {
            if(symptom.getId() == 0) {
                symptom = symptomRepository.save(symptom);
            }
            newSymptomList.add(symptomRepository.findById(symptom.getId()).get());
        }
        user.setSymptoms(newSymptomList);

        List<PatientChronicIllness> patientChronicIllnessList = user.getPatientChronicIllness();
        for(PatientChronicIllness patientChronicIllness : patientChronicIllnessList){
            patientChronicIllness.setUser(user);
            CompositeKeyPatientChronicIllness compositeKey;
            if(patientChronicIllness.getChronicIllness().getId() == 0){
                ChronicIllness chronicIllness = chronicIllnessRepository.save(patientChronicIllness.getChronicIllness());
                compositeKey = new CompositeKeyPatientChronicIllness(user.getId(), chronicIllness.getId());
            }
            else{
                ChronicIllness chronicIllness = chronicIllnessRepository.findById(patientChronicIllness.getChronicIllness().getId()).get();
                patientChronicIllness.setChronicIllness(chronicIllness);
                compositeKey = new CompositeKeyPatientChronicIllness(user.getId(), patientChronicIllness.getChronicIllness().getId());
            }
            patientChronicIllness.setId(compositeKey);
        }

        return userRepository.save(user);
    }

    @Override
    public User updateUser(int id, User user) {
        Optional<User> oldUser = userRepository.findById(id);
        if(oldUser.isPresent()) {
            User oldUserObj = oldUser.get();
            user.setId(oldUserObj.getId());
            return createUser(user);
        }
        return null;
    }

    @Override
    public User deleteUser(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            userRepository.delete(optionalUser.get());
            return optionalUser.get();
        }
        return null;
    }
}
