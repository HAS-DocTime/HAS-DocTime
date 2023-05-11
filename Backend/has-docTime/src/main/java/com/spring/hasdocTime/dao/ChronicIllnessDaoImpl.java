package com.spring.hasdocTime.dao;


import com.spring.hasdocTime.entity.ChronicIllness;
import com.spring.hasdocTime.entity.PatientChronicIllness;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.ChronicIllnessInterface;
import com.spring.hasdocTime.interfc.PatientChronicIllnessInterface;
import com.spring.hasdocTime.repository.ChronicIllnessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChronicIllnessDaoImpl implements ChronicIllnessInterface {


    private ChronicIllnessRepository chronicIllnessRepository;
    private PatientChronicIllnessInterface patientChronicIllnessDao;

    @Autowired
    public ChronicIllnessDaoImpl(ChronicIllnessRepository theChronicIllnessRepository, PatientChronicIllnessInterface patientChronicIllnessDao){
        this.patientChronicIllnessDao = patientChronicIllnessDao;
        this.chronicIllnessRepository = theChronicIllnessRepository;
    }

    @Override
    public ChronicIllness createChronicIllness(ChronicIllness chronicIllness) throws MissingParameterException{
        if(chronicIllness.getName()==null || chronicIllness.getName().equals("")){
            throw new MissingParameterException("Chronic Illness");
        }
        return chronicIllnessRepository.save(chronicIllness);
    }

    @Override
    public List<ChronicIllness> getAllChronicIllness() {
        return chronicIllnessRepository.findAll();
    }

    @Override
    public ChronicIllness getChronicIllness(int id) throws DoesNotExistException {
        Optional<ChronicIllness> optionalChronicIllness = chronicIllnessRepository.findById(id);
        if(optionalChronicIllness.isPresent()) {
            return optionalChronicIllness.get();
        }
        throw new DoesNotExistException("Chronic Illness");
    }

    @Override
    public ChronicIllness updateChronicIllness(int id, ChronicIllness chronicIllness) throws DoesNotExistException, MissingParameterException {
        if(chronicIllness.getName()==null || chronicIllness.getName().equals("")){
            throw new MissingParameterException("Chronic Illness");
        }
        Optional<ChronicIllness> optionalChronicIllness = chronicIllnessRepository.findById(id);
        if(optionalChronicIllness.isPresent()){
            ChronicIllness oldChronicIllness = optionalChronicIllness.get();
            chronicIllness.setId(oldChronicIllness.getId());
            chronicIllnessRepository.save(chronicIllness);
            return chronicIllness;
        }
        throw new DoesNotExistException("Chronic Illness");
    }

    @Override
    public boolean deleteChronicIllness(int id) throws DoesNotExistException{
        Optional<ChronicIllness> optionalChronicIllness = chronicIllnessRepository.findById(id);
        if(optionalChronicIllness.isPresent()) {
            for(PatientChronicIllness patientChronicIllness : optionalChronicIllness.get().getPatientChronicIllnesses()){
                patientChronicIllnessDao.deletePatientChronicIllness(patientChronicIllness.getId());
            }
            
            chronicIllnessRepository.deleteById(id);
            return true;
        }
        throw new DoesNotExistException("Chronic Illness");
    }
}
