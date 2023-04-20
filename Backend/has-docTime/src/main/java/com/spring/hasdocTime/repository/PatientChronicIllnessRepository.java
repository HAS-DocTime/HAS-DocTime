package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.CompositeKeyPatientChronicIllness;
import com.spring.hasdocTime.entity.PatientChronicIllness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientChronicIllnessRepository extends JpaRepository<PatientChronicIllness, CompositeKeyPatientChronicIllness> {
    
    @Modifying
    @Query("DELETE FROM PatientChronicIllness p where p.id = :id")
    void deleteById(@Param("id") CompositeKeyPatientChronicIllness id);
}
