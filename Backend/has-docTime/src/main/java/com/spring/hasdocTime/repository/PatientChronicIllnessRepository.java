package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.CompositeKeyPatientChronicIllness;
import com.spring.hasdocTime.entity.PatientChronicIllness;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientChronicIllnessRepository extends JpaRepository<PatientChronicIllness, CompositeKeyPatientChronicIllness> {
}
