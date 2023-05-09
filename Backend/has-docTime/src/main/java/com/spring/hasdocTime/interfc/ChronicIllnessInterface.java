package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.ChronicIllness;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;

import java.util.List;

public interface ChronicIllnessInterface {


    public ChronicIllness createChronicIllness(ChronicIllness chronicIllness);

    public List<ChronicIllness> getAllChronicIllness();

    public ChronicIllness getChronicIllness(int id) throws DoesNotExistException;

    public ChronicIllness updateChronicIllness(int id, ChronicIllness chronicIllness) throws DoesNotExistException;

    public boolean deleteChronicIllness(int id) throws DoesNotExistException;

}
