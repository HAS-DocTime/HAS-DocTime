package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.ChronicIllness;

import java.util.List;

public interface ChronicIllnessInterface {


    public ChronicIllness createChronicIllness(ChronicIllness chronicIllness);

    public List<ChronicIllness> getAllChronicIllness();

    public ChronicIllness getChronicIllness(int id);

    public ChronicIllness updateChronicIllness(int id, ChronicIllness chronicIllness);

    public boolean deleteChronicIllness(int id);

}
