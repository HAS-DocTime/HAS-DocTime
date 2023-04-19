package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.Symptom;
import com.spring.hasdocTime.interfc.SymptomInterface;
import com.spring.hasdocTime.repository.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SymptomDaoImpl implements SymptomInterface {

    @Autowired
    private SymptomRepository symptomRepository;

    @Override
    public Symptom getSymptom(int id) {
        Optional<Symptom> optionalSymptom = symptomRepository.findById(id);
        Symptom s = null;
        if(optionalSymptom.isPresent()){
            s = optionalSymptom.get();
        }
        return s;
    }

    @Override
    public List<Symptom> getAllSymptom() {
        return symptomRepository.findAll();
    }

    @Override
    public Symptom createSymptom(Symptom symptom) {
        symptom.setId(0);
        return symptomRepository.save(symptom);
    }

    @Override
    public Symptom updateSymptom(int id, Symptom symptom) {
        Optional<Symptom> optionalSymptom = symptomRepository.findById(id);
        Symptom s = null;
        if(optionalSymptom.isPresent()){
            symptom.setId(id);
            s = symptomRepository.save(symptom);
        }
        return s;
    }

    @Override
    public boolean deleteSymptom(int id) {
        Optional<Symptom> optionalSymptom = symptomRepository.findById(id);
        if(optionalSymptom.isPresent()){
            symptomRepository.delete(optionalSymptom.get());
            return true;
        }
        return false;
    }
}
