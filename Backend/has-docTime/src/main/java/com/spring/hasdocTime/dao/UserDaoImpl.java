package com.spring.hasdocTime.dao;


import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.PatientChronicIllnessInterface;
import com.spring.hasdocTime.interfaces.UserInterface;
import com.spring.hasdocTime.repository.ChronicIllnessRepository;
import com.spring.hasdocTime.repository.SymptomRepository;
import com.spring.hasdocTime.repository.UserRepository;
import com.spring.hasdocTime.utills.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.hibernate.Hibernate;
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

    /**
     * Retrieves all users.
     *
     * @return The list of all users.
     */
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

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user.
     * @return The user with the specified ID.
     * @throws DoesNotExistException if the user does not exist.
     */
    @Override
    public User getUser(int id) throws DoesNotExistException{
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            Hibernate.initialize(user.get().getPatientChronicIllness());
            return user.get();

        }
        throw new DoesNotExistException("User");
    }

    /**
     * Updates a user with the provided data, including the password.
     *
     * @param user The updated user object.
     * @return The updated user.
     * @throws DoesNotExistException     if the user does not exist.
     * @throws MissingParameterException if any required parameter is missing.
     */
    public User updateUserWithPassword(User user) throws DoesNotExistException, MissingParameterException{
        // Validate required parameters
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

        // Validate and update symptoms
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

        // Validate and update patient chronic illnesses
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
        // Save the updated user
        return userRepository.save(user);
    }



    /**
     * Creates a new user with the provided data, including the password.
     *
     * @param user The user object to create.
     * @return The created user.
     * @throws MissingParameterException if any required parameter is missing.
     * @throws DoesNotExistException     if the chronic illness does not exist.
     */
    @Override
    public User createUser(User user) throws MissingParameterException, DoesNotExistException{
        // Validate required parameters
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

        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Create or update the user
        return updateUserWithPassword(user);
    }


    /**
     * Updates a user with the provided data.
     *
     * @param id   The ID of the user to update.
     * @param user The updated user object.
     * @return The updated user.
     * @throws DoesNotExistException     if the user does not exist.
     * @throws MissingParameterException if any required parameter is missing.
     */
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

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @return The deleted user.
     * @throws DoesNotExistException if the user does not exist.
     */
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

    /**
     * Retrieves a user by their email.
     *
     * @param email The email of the user.
     * @return The user with the specified email.
     * @throws DoesNotExistException if the user does not exist.
     */
    @Override
    public User getUserByEmail(String email) throws DoesNotExistException{
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new DoesNotExistException("User");
        }
        return user.get();
    }

    /**
     * Retrieves all patients.
     *
     * @return The list of all patients.
     */
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

    /**
     * Retrieves patients with a specific chronic illness.
     *
     * @param id The ID of the chronic illness.
     * @return A set of users who have the specified chronic illness.
     * @throws DoesNotExistException if the chronic illness does not exist.
     */
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



}
