package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.ChronicIllness;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.interfc.ChronicIllnessInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChronicIllnessServiceImpl implements ChronicIllnessInterface{

    private ChronicIllnessInterface chronicIllnessDao;

    @Autowired
    public ChronicIllnessServiceImpl(@Qualifier("chronicIllnessDaoImpl") ChronicIllnessInterface theChronicIllnessDao) {
        this.chronicIllnessDao = theChronicIllnessDao;
    }

    @Override
    public ChronicIllness createChronicIllness(ChronicIllness chronicIllness) {
        return chronicIllnessDao.createChronicIllness(chronicIllness);
    }

    @Override
    public List<ChronicIllness> getAllChronicIllness() {
        return chronicIllnessDao.getAllChronicIllness();
    }

    @Override
    public ChronicIllness getChronicIllness(int id) throws DoesNotExistException {
        return chronicIllnessDao.getChronicIllness(id);
    }

    @Override
    public ChronicIllness updateChronicIllness(int id, ChronicIllness chronicIllness) throws DoesNotExistException{
        return chronicIllnessDao.updateChronicIllness(id, chronicIllness);
    }

    @Override
    public boolean deleteChronicIllness(int id) throws DoesNotExistException{
        return chronicIllnessDao.deleteChronicIllness(id);
    }
}
