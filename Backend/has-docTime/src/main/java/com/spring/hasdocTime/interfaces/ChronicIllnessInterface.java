package com.spring.hasdocTime.interfaces;

import com.spring.hasdocTime.entity.ChronicIllness;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;

import java.util.List;

public interface ChronicIllnessInterface {


    public ChronicIllness createChronicIllness(ChronicIllness chronicIllness) throws MissingParameterException;

    public List<ChronicIllness> getAllChronicIllness();

    public ChronicIllness getChronicIllness(int id) throws DoesNotExistException;

    public ChronicIllness updateChronicIllness(int id, ChronicIllness chronicIllness) throws DoesNotExistException, MissingParameterException;

    public boolean deleteChronicIllness(int id) throws DoesNotExistException;

}
