/**
 * Interface defining operations for managing chronic illness entities.
 */
package com.spring.hasdoctime.interfaces;

import com.spring.hasdoctime.entity.ChronicIllness;
import com.spring.hasdoctime.exceptionhandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionhandling.exception.MissingParameterException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChronicIllnessInterface {

    /**
     * Creates a new chronic illness.
     *
     * @param chronicIllness the ChronicIllness object representing the new chronic illness
     * @return the created ChronicIllness object
     * @throws MissingParameterException if there are missing parameters in the chronic illness object
     */
    public ChronicIllness createChronicIllness(ChronicIllness chronicIllness) throws MissingParameterException;

    public Page<ChronicIllness> getAllChronicIllness(int page, int size, String sortBy, String search);

    /**
     * Retrieves a list of all chronic illnesses.
     *
     * @return List of ChronicIllness objects representing all chronic illnesses
     */
    public List<ChronicIllness> getAllChronicIllnesses();

    /**
     * Retrieves the chronic illness with the specified ID.
     *
     * @param id the ID of the chronic illness to retrieve
     * @return the ChronicIllness object representing the chronic illness
     * @throws DoesNotExistException if the chronic illness does not exist
     */
    public ChronicIllness getChronicIllness(int id) throws DoesNotExistException;

    /**
     * Updates the chronic illness with the specified ID.
     *
     * @param id             the ID of the chronic illness to update
     * @param chronicIllness the updated ChronicIllness object
     * @return the updated ChronicIllness object
     * @throws DoesNotExistException     if the chronic illness does not exist
     * @throws MissingParameterException if there are missing parameters in the chronic illness object
     */
    public ChronicIllness updateChronicIllness(int id, ChronicIllness chronicIllness) throws DoesNotExistException, MissingParameterException;

    /**
     * Deletes the chronic illness with the specified ID.
     *
     * @param id the ID of the chronic illness to delete
     * @return true if the chronic illness is deleted successfully, false otherwise
     * @throws DoesNotExistException if the chronic illness does not exist
     */
    public boolean deleteChronicIllness(int id) throws DoesNotExistException;

}
