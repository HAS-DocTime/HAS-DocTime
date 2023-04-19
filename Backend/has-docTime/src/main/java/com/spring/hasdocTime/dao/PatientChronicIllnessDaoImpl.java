package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.CompositeKeyPatientChronicIllness;
import com.spring.hasdocTime.entity.PatientChronicIllness;
import com.spring.hasdocTime.interfc.PatientChronicIllnessInterface;
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
    public PatientChronicIllness getPatientChronicIllness(CompositeKeyPatientChronicIllness id) {
        Optional<PatientChronicIllness> patientChronicIllness = patientChronicIllnessRepository.findById(id);
        if(patientChronicIllness.isPresent()){
            PatientChronicIllness patientChronicIllnessObj = patientChronicIllness.get();
            return patientChronicIllnessObj;
        }
        return null;
    }

    @Override
    public PatientChronicIllness createPatientChronicIllness(PatientChronicIllness patientChronicIllness) {
        return patientChronicIllnessRepository.save(patientChronicIllness);
    }

    @Override
    public PatientChronicIllness updatePatientChronicIllness(CompositeKeyPatientChronicIllness id, PatientChronicIllness patientChronicIllness) {
        patientChronicIllness.setId(id);
        System.out.println(id.getPatientId());
        System.out.println(id.getChronicIllnessId());
        return patientChronicIllnessRepository.save(patientChronicIllness);
    }

    @Override
    public void deletePatientChronicIllness(CompositeKeyPatientChronicIllness id) {
        Optional<PatientChronicIllness> patientChronicIllness =  patientChronicIllnessRepository.findById(id);
        if(patientChronicIllness.isPresent()) {
            patientChronicIllnessRepository.deleteById(id);
            System.out.println("Deleted  this chronicIllness of id:" + id.getChronicIllnessId() + " from this patient of id: " + id.getPatientId());
        }

    }
}
