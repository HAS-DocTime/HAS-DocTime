package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.ChronicIllness;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.ChronicIllnessInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
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
    public ChronicIllness createChronicIllness(ChronicIllness chronicIllness) throws MissingParameterException{
        return chronicIllnessDao.createChronicIllness(chronicIllness);
    }

    @Override
    public Page<ChronicIllness> getAllChronicIllness(int page, int size, String sortBy, String search) {
        return chronicIllnessDao.getAllChronicIllness(page, size, sortBy, search);
    }

    @Override
    public List<ChronicIllness> getAllChronicIllnesses() {
        return chronicIllnessDao.getAllChronicIllnesses();
    }

    @Override
    public ChronicIllness getChronicIllness(int id) throws DoesNotExistException {
        return chronicIllnessDao.getChronicIllness(id);
    }

    @Override
    public ChronicIllness updateChronicIllness(int id, ChronicIllness chronicIllness) throws DoesNotExistException, MissingParameterException {
        return chronicIllnessDao.updateChronicIllness(id, chronicIllness);
    }

    @Override
    public boolean deleteChronicIllness(int id) throws DoesNotExistException{
        return chronicIllnessDao.deleteChronicIllness(id);
    }
}
