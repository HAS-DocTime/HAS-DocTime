package com.spring.hasdoctime.dao;

import com.spring.hasdoctime.entity.CompositeKeyPatientChronicIllness;
import com.spring.hasdoctime.entity.PatientChronicIllness;
import com.spring.hasdoctime.exceptionhandling.exception.DoesNotExistException;
import com.spring.hasdoctime.interfaces.PatientChronicIllnessInterface;
import com.spring.hasdoctime.repository.PatientChronicIllnessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * Implementation of the PatientChronicIllnessInterface that provides CRUD operations for patient chronic illness.
 */
@Service
public class PatientChronicIllnessDaoImpl implements PatientChronicIllnessInterface {

    private PatientChronicIllnessRepository patientChronicIllnessRepository;

    @Autowired
    public PatientChronicIllnessDaoImpl(PatientChronicIllnessRepository thePatientChronicIllnessRepository) {
        this.patientChronicIllnessRepository = thePatientChronicIllnessRepository;
    }

    /**
     * Retrieves all patient chronic illnesses.
     *
     * @return a list of patient chronic illnesses
     */
    @Override
    public List<PatientChronicIllness> getAllPatientChronicIllness() {
        return patientChronicIllnessRepository.findAll();
    }

    /**
     * Retrieves a specific patient chronic illness by its composite key.
     *
     * @param id the composite key of the patient chronic illness
     * @return the patient chronic illness with the specified composite key
     * @throws DoesNotExistException if the patient chronic illness does not exist
     */
    @Override
    public PatientChronicIllness getPatientChronicIllness(CompositeKeyPatientChronicIllness id) throws DoesNotExistException {
        Optional<PatientChronicIllness> patientChronicIllness = patientChronicIllnessRepository.findById(id);
        if(patientChronicIllness.isPresent()){
            return patientChronicIllness.get();
        }
        throw new DoesNotExistException("Patient Chronic Illness");
    }

    /**
     * Creates a new patient chronic illness.
     *
     * @param patientChronicIllness the patient chronic illness to create
     * @return the created patient chronic illness
     */
    @Transactional
    @Override
    public PatientChronicIllness createPatientChronicIllness(PatientChronicIllness patientChronicIllness) {
        return patientChronicIllnessRepository.save(patientChronicIllness);
    }

    /**
     * Updates an existing patient chronic illness.
     *
     * @param id                   the composite key of the patient chronic illness to update
     * @param patientChronicIllness the updated patient chronic illness data
     * @return the updated patient chronic illness
     * @throws DoesNotExistException if the patient chronic illness does not exist
     */
    @Transactional
    @Override
    public PatientChronicIllness updatePatientChronicIllness(CompositeKeyPatientChronicIllness id, PatientChronicIllness patientChronicIllness) throws DoesNotExistException{
        Optional<PatientChronicIllness> oldPatientChronicIllness = patientChronicIllnessRepository.findById(id);
        if(oldPatientChronicIllness.isEmpty()){
            throw new DoesNotExistException("Patient Chronic Illness");
        }
        patientChronicIllness.setId(id);
        return patientChronicIllnessRepository.save(patientChronicIllness);
    }

    /**
     * Deletes a patient chronic illness.
     *
     * @param id the composite key of the patient chronic illness to delete
     * @throws DoesNotExistException if the patient chronic illness does not exist
     */
    @Transactional
    @Override
    public void deletePatientChronicIllness(CompositeKeyPatientChronicIllness id) throws DoesNotExistException{
        Optional<PatientChronicIllness> patientChronicIllness =  patientChronicIllnessRepository.findById(id);
        if(patientChronicIllness.isPresent()) {
            patientChronicIllnessRepository.deleteById(id);
        }
        else{
            throw new DoesNotExistException("Patient Chronic Illness");
        }

    }
}
