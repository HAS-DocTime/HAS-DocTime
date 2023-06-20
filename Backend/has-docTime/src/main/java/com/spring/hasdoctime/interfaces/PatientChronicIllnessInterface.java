/**
 * Interface defining operations for patient chronic illness.
 */
package com.spring.hasdoctime.interfaces;

import com.spring.hasdoctime.entity.CompositeKeyPatientChronicIllness;
import com.spring.hasdoctime.entity.PatientChronicIllness;
import com.spring.hasdoctime.exceptionhandling.exception.DoesNotExistException;

import java.util.List;

public interface PatientChronicIllnessInterface {

    /**
     * Retrieves a list of all patient chronic illnesses.
     *
     * @return a list of PatientChronicIllness objects representing all patient chronic illnesses
     */
    List<PatientChronicIllness> getAllPatientChronicIllness();

    /**
     * Retrieves the patient chronic illness with the specified composite key.
     *
     * @param id the composite key of the patient chronic illness
     * @return the PatientChronicIllness object representing the patient chronic illness
     * @throws DoesNotExistException if the patient chronic illness does not exist
     */
    PatientChronicIllness getPatientChronicIllness(CompositeKeyPatientChronicIllness id) throws DoesNotExistException;

    /**
     * Creates a new patient chronic illness.
     *
     * @param patientChronicIllness the PatientChronicIllness object representing the patient chronic illness to be created
     * @return the created PatientChronicIllness object
     */
    PatientChronicIllness createPatientChronicIllness(PatientChronicIllness patientChronicIllness);

    /**
     * Updates the patient chronic illness with the specified composite key.
     *
     * @param id                    the composite key of the patient chronic illness
     * @param patientChronicIllness the updated PatientChronicIllness object
     * @return the updated PatientChronicIllness object
     * @throws DoesNotExistException if the patient chronic illness does not exist
     */
    PatientChronicIllness updatePatientChronicIllness(CompositeKeyPatientChronicIllness id, PatientChronicIllness patientChronicIllness) throws DoesNotExistException;

    /**
     * Deletes the patient chronic illness with the specified composite key.
     *
     * @param id the composite key of the patient chronic illness
     * @throws DoesNotExistException if the patient chronic illness does not exist
     */
    void deletePatientChronicIllness(CompositeKeyPatientChronicIllness id) throws DoesNotExistException;
}
