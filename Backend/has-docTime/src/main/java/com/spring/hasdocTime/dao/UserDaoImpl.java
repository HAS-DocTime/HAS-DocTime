package com.spring.hasdocTime.dao;


import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.interfc.PatientChronicIllnessInterface;
import com.spring.hasdocTime.interfc.UserInterface;
import com.spring.hasdocTime.repository.ChronicIllnessRepository;
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

    private PatientChronicIllnessInterface patientChronicIllnessDao;

    private ChronicIllnessRepository chronicIllnessRepository;

    private SymptomRepository symptomRepository;

    @Autowired
    public UserDaoImpl(UserRepository userRepository, PatientChronicIllnessInterface patientChronicIllnessDao,ChronicIllnessRepository chronicIllnessRepository, SymptomRepository symptomRepository) {
        this.userRepository = userRepository;
        this.patientChronicIllnessDao = patientChronicIllnessDao;
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
        if(symptomList!=null){
            List<Symptom> newSymptomList = new ArrayList<>();
        for(Symptom symptom : symptomList) {
            newSymptomList.add(symptomRepository.findById(symptom.getId()).get());
        }
        user.setSymptoms(newSymptomList);
        }
        List<PatientChronicIllness> patientChronicIllnessList = user.getPatientChronicIllness();
        if(patientChronicIllnessList != null){
            for(PatientChronicIllness patientChronicIllness : patientChronicIllnessList){
            patientChronicIllness.setUser(user);
            CompositeKeyPatientChronicIllness compositeKey;
            ChronicIllness chronicIllness = chronicIllnessRepository.findById(patientChronicIllness.getChronicIllness().getId()).get();
            patientChronicIllness.setChronicIllness(chronicIllness);
            compositeKey = new CompositeKeyPatientChronicIllness(user.getId(), patientChronicIllness.getChronicIllness().getId());
            patientChronicIllness.setId(compositeKey);
            user.setPatientChronicIllness(patientChronicIllnessList);
            }
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(int id, User user) {
        Optional<User> oldUser = userRepository.findById(id);
        if(oldUser.isPresent()) {
            User oldUserObj = oldUser.get();
            user.setId(oldUserObj.getId());
            for(PatientChronicIllness patientChronicIllness : oldUser.get().getPatientChronicIllness()){
                patientChronicIllnessDao.deletePatientChronicIllness(patientChronicIllness.getId());
            }
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
