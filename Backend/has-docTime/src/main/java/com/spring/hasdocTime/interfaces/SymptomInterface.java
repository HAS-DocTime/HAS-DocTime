/**
 * Interface defining operations for symptoms.
 */
package com.spring.hasdocTime.interfaces;

import com.spring.hasdocTime.entity.Symptom;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;

import java.util.List;

public interface SymptomInterface {

    /**
     * Retrieves a symptom by ID.
     *
     * @param id the ID of the symptom
     * @return the Symptom object representing the retrieved symptom
     * @throws DoesNotExistException if the symptom does not exist
     */
    Symptom getSymptom(int id) throws DoesNotExistException;

    /**
     * Retrieves all symptoms.
     *
     * @return a list of Symptom objects representing all symptoms
     */
    List<Symptom> getAllSymptom();

    /**
     * Creates a new symptom.
     *
     * @param symptom the Symptom object representing the symptom to be created
     * @return the Symptom object representing the created symptom
     * @throws DoesNotExistException    if related entities do not exist
     * @throws MissingParameterException if required parameters are missing
     */
    Symptom createSymptom(Symptom symptom) throws DoesNotExistException, MissingParameterException;

    /**
     * Updates a symptom.
     *
     * @param id      the ID of the symptom to be updated
     * @param symptom the Symptom object representing the updated symptom
     * @return the Symptom object representing the updated symptom
     * @throws DoesNotExistException    if the symptom does not exist
     * @throws MissingParameterException if required parameters are missing
     */
    Symptom updateSymptom(int id, Symptom symptom) throws DoesNotExistException, MissingParameterException;

    /**
     * Deletes a symptom.
     *
     * @param id the ID of the symptom to be deleted
     * @return true if the symptom is successfully deleted, false otherwise
     * @throws DoesNotExistException if the symptom does not exist
     */
    boolean deleteSymptom(int id) throws DoesNotExistException;
}
