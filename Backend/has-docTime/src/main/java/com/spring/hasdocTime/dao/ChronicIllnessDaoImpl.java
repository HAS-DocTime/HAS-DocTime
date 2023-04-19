package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.ChronicIllness;
import com.spring.hasdocTime.interfc.ChronicIllnessInterface;
import com.spring.hasdocTime.repository.ChronicIllnessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChronicIllnessDaoImpl implements ChronicIllnessInterface {

    @Autowired
    private ChronicIllnessRepository chronicIllnessRepository;

    @Override
    public ChronicIllness createChronicIllness(ChronicIllness chronicIllness) {
        chronicIllness.setId(0);
        return chronicIllnessRepository.save(chronicIllness);
    }

    @Override
    public List<ChronicIllness> getAllChronicIllness() {
        return chronicIllnessRepository.findAll();
    }

    @Override
    public ChronicIllness getChronicIllness(int id) {
        Optional<ChronicIllness> optionalChronicIllness = chronicIllnessRepository.findById(id);
        ChronicIllness chronicIllness = null;
        if(optionalChronicIllness.isPresent()) {
            chronicIllness = optionalChronicIllness.get();
        }
        return chronicIllness;
    }

    @Override
    public ChronicIllness updateChronicIllness(int id, ChronicIllness chronicIllness) {
        Optional<ChronicIllness> optionalChronicIllness = chronicIllnessRepository.findById(id);
        ChronicIllness c = null;
        if(optionalChronicIllness.isPresent()){
            c.setId(id);
            c = chronicIllnessRepository.save(chronicIllness);
        }
        return c;
    }

    @Override
    public boolean deleteChronicIllness(int id) {
        Optional<ChronicIllness> optionalChronicIllness = chronicIllnessRepository.findById(id);
        if(optionalChronicIllness.isPresent()) {
            chronicIllnessRepository.delete(optionalChronicIllness.get());
            return true;
        }
        return false;
    }
}
