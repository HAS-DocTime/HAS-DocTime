package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.CompositeKeyPatientChronicIllness;
import com.spring.hasdocTime.entity.PatientChronicIllness;

import java.util.List;

public interface PatientChronicIllnessInterface {
    List<PatientChronicIllness> getAllPatientChronicIllness();
    //
    PatientChronicIllness getPatientChronicIllness(CompositeKeyPatientChronicIllness id);

    PatientChronicIllness createPatientChronicIllness(PatientChronicIllness patientChronicIllness);

    PatientChronicIllness updatePatientChronicIllness(CompositeKeyPatientChronicIllness id, PatientChronicIllness patientChronicIllness);

    void deletePatientChronicIllness(CompositeKeyPatientChronicIllness id);
}
