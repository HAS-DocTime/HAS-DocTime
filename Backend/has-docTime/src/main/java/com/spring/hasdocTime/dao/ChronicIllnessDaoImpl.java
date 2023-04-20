package com.spring.hasdocTime.dao;

import com.electronwill.nightconfig.core.conversion.SpecClassInArray;
import com.spring.hasdocTime.entity.ChronicIllness;
import com.spring.hasdocTime.entity.PatientChronicIllness;
import com.spring.hasdocTime.interfc.ChronicIllnessInterface;
import com.spring.hasdocTime.repository.ChronicIllnessRepository;
import com.spring.hasdocTime.repository.PatientChronicIllnessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChronicIllnessDaoImpl implements ChronicIllnessInterface {


    private ChronicIllnessRepository chronicIllnessRepository;
    private PatientChronicIllnessRepository patientChronicIllnessRepository;

    @Autowired
    public ChronicIllnessDaoImpl(ChronicIllnessRepository theChronicIllnessRepository, PatientChronicIllnessRepository thePatientChronicIllnessRepository){
        this.patientChronicIllnessRepository = thePatientChronicIllnessRepository;
        this.chronicIllnessRepository = theChronicIllnessRepository;
    }

    @Override
    public ChronicIllness createChronicIllness(ChronicIllness chronicIllness) {
        return chronicIllnessRepository.save(chronicIllness);
    }

    @Override
    public List<ChronicIllness> getAllChronicIllness() {
        return chronicIllnessRepository.findAll();
    }

    @Override
    public ChronicIllness getChronicIllness(int id) {
        Optional<ChronicIllness> optionalChronicIllness = chronicIllnessRepository.findById(id);
        if(optionalChronicIllness.isPresent()) {
            return optionalChronicIllness.get();
        }
        return null;
    }

    @Override
    public ChronicIllness updateChronicIllness(int id, ChronicIllness chronicIllness) {
        Optional<ChronicIllness> optionalChronicIllness = chronicIllnessRepository.findById(id);
        if(optionalChronicIllness.isPresent()){
            ChronicIllness oldChronicIllness = optionalChronicIllness.get();
            chronicIllness.setId(oldChronicIllness.getId());
            chronicIllnessRepository.save(chronicIllness);
            return chronicIllness;
        }
        return null;
    }

    @Override
    public boolean deleteChronicIllness(int id) {
        Optional<ChronicIllness> optionalChronicIllness = chronicIllnessRepository.findById(id);
        if(optionalChronicIllness.isPresent()) {
            chronicIllnessRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
