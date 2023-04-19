package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.Symptom;

import java.util.List;

public interface SymptomInterface {

    public Symptom getSymptom(int id);

    public List<Symptom> getAllSymptom();

    public Symptom createSymptom(Symptom symptom);

    public Symptom updateSymptom(int id, Symptom symptom);

    public boolean deleteSymptom(int id);
}
