package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.Symptom;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.SymptomInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SymptomServiceImpl implements SymptomInterface{

    @Autowired
    @Qualifier("symptomDaoImpl")
    private SymptomInterface symptomDao;

    @Override
    public Symptom getSymptom(int id) throws DoesNotExistException{
        return symptomDao.getSymptom(id);
    }

    @Override
    public Page<Symptom> getAllSymptom(int page, int size, String sortBy, String search) {
        return symptomDao.getAllSymptom(page, size, sortBy, search);
    }

    @Override
    public Symptom createSymptom(Symptom symptom) throws DoesNotExistException, MissingParameterException{
        return symptomDao.createSymptom(symptom);
    }

    @Override
    public Symptom updateSymptom(int id, Symptom symptom) throws DoesNotExistException, MissingParameterException {
        return symptomDao.updateSymptom(id, symptom);
    }

    @Override
    public boolean deleteSymptom(int id) throws DoesNotExistException{
        return symptomDao.deleteSymptom(id);
    }
}
