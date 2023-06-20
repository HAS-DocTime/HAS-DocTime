/**
 * Repository interface for accessing and manipulating PatientChronicIllness entities.
 */
package com.spring.hasdoctime.repository;

import com.spring.hasdoctime.entity.CompositeKeyPatientChronicIllness;
import com.spring.hasdoctime.entity.PatientChronicIllness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientChronicIllnessRepository extends JpaRepository<PatientChronicIllness, CompositeKeyPatientChronicIllness> {

    /**
     * Deletes a patient's chronic illness record by ID.
     *
     * @param id the composite key of the patient's chronic illness record to be deleted
     */
    @Modifying
    @Query("DELETE FROM PatientChronicIllness p WHERE p.id = :id")
    void deleteById(@Param("id") CompositeKeyPatientChronicIllness id);
}
