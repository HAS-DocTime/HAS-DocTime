package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.Symptom;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SymptomInterface {

    public Symptom getSymptom(int id) throws DoesNotExistException;

    public List<Symptom> getAllSymptom();

    public Symptom createSymptom(Symptom symptom) throws DoesNotExistException;

    public Symptom updateSymptom(int id, Symptom symptom) throws DoesNotExistException;

    public boolean deleteSymptom(int id) throws DoesNotExistException;
}
