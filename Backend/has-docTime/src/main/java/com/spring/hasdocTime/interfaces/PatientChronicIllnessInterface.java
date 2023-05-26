package com.spring.hasdocTime.interfaces;

import com.spring.hasdocTime.entity.CompositeKeyPatientChronicIllness;
import com.spring.hasdocTime.entity.PatientChronicIllness;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;

import java.util.List;

public interface PatientChronicIllnessInterface {
    List<PatientChronicIllness> getAllPatientChronicIllness();
    //
    PatientChronicIllness getPatientChronicIllness(CompositeKeyPatientChronicIllness id) throws DoesNotExistException;

    PatientChronicIllness createPatientChronicIllness(PatientChronicIllness patientChronicIllness);

    PatientChronicIllness updatePatientChronicIllness(CompositeKeyPatientChronicIllness id, PatientChronicIllness patientChronicIllness) throws DoesNotExistException;

    void deletePatientChronicIllness(CompositeKeyPatientChronicIllness id) throws DoesNotExistException;
}
