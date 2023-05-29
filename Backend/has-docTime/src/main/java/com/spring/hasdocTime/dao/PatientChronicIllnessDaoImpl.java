package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.CompositeKeyPatientChronicIllness;
import com.spring.hasdocTime.entity.PatientChronicIllness;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.interfaces.PatientChronicIllnessInterface;
import com.spring.hasdocTime.repository.PatientChronicIllnessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientChronicIllnessDaoImpl implements PatientChronicIllnessInterface {

    private PatientChronicIllnessRepository patientChronicIllnessRepository;

    @Autowired
    public PatientChronicIllnessDaoImpl(PatientChronicIllnessRepository thePatientChronicIllnessRepository) {
        this.patientChronicIllnessRepository = thePatientChronicIllnessRepository;
    }

    @Override
    public List<PatientChronicIllness> getAllPatientChronicIllness() {
        return patientChronicIllnessRepository.findAll();
    }

    @Override
    public PatientChronicIllness getPatientChronicIllness(CompositeKeyPatientChronicIllness id) throws DoesNotExistException {
        Optional<PatientChronicIllness> patientChronicIllness = patientChronicIllnessRepository.findById(id);
        if(patientChronicIllness.isPresent()){
            return patientChronicIllness.get();
        }
        throw new DoesNotExistException("Patient Chronic Illness");
    }

    @Override
    public PatientChronicIllness createPatientChronicIllness(PatientChronicIllness patientChronicIllness) {
        return patientChronicIllnessRepository.save(patientChronicIllness);
    }

    @Override
    public PatientChronicIllness updatePatientChronicIllness(CompositeKeyPatientChronicIllness id, PatientChronicIllness patientChronicIllness) throws DoesNotExistException{
        Optional<PatientChronicIllness> oldPatientChronicIllness = patientChronicIllnessRepository.findById(id);
        if(oldPatientChronicIllness.isEmpty()){
            throw new DoesNotExistException("Patient Chronic Illness");
        }
        patientChronicIllness.setId(id);
        return patientChronicIllnessRepository.save(patientChronicIllness);
    }

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
