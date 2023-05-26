package com.spring.hasdocTime.interfaces;

import com.spring.hasdocTime.entity.Symptom;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;

import java.util.List;

public interface SymptomInterface {

    public Symptom getSymptom(int id) throws DoesNotExistException;

    public List<Symptom> getAllSymptom();

    public Symptom createSymptom(Symptom symptom) throws DoesNotExistException, MissingParameterException;

    public Symptom updateSymptom(int id, Symptom symptom) throws DoesNotExistException, MissingParameterException;

    public boolean deleteSymptom(int id) throws DoesNotExistException;
}
