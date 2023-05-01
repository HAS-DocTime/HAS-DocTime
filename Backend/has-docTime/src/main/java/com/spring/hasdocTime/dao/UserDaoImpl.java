package com.spring.hasdocTime.dao;


import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.interfc.PatientChronicIllnessInterface;
import com.spring.hasdocTime.interfc.UserInterface;
import com.spring.hasdocTime.repository.ChronicIllnessRepository;
import com.spring.hasdocTime.repository.SymptomRepository;
import com.spring.hasdocTime.repository.UserRepository;
import com.spring.hasdocTime.security.AuthResponse;
import com.spring.hasdocTime.security.JwtService;
import com.spring.hasdocTime.security.RegisterRequest;
import com.spring.hasdocTime.security.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


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

    public RegisterResponse register(User request) {

        var user = User.builder()
                .email(request.getEmail())
                .age(request.getAge())
                .dob(request.getDob())
                .contact(request.getContact())
                .bloodGroup(request.getBloodGroup())
                .height(request.getHeight())
                .weight(request.getWeight())
                .name(request.getName())
                .gender(request.getGender())
                .patientChronicIllness(request.getPatientChronicIllness())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();


        var savedUser = userRepository.save(user);
        int id = userRepository.findByEmail(request.getEmail()).get().getId();

        var jwtToken = jwtService.generateToken(user);
        return RegisterResponse.builder()
                .token(jwtToken)
                .id(id)
                .build();

    }

    public AuthResponse authenticate(RegisterRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();

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
