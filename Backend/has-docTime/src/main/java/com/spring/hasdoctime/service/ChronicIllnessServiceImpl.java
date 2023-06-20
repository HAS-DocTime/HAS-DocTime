package com.spring.hasdoctime.service;

import com.spring.hasdoctime.entity.ChronicIllness;
import com.spring.hasdoctime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdoctime.interfaces.ChronicIllnessInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link ChronicIllnessInterface} interface that provides the business logic for managing chronic illnesses.
 */
@Service
public class ChronicIllnessServiceImpl implements ChronicIllnessInterface {

    private ChronicIllnessInterface chronicIllnessDao;

    /**
     * Constructs a new {@code ChronicIllnessServiceImpl} with the specified {@link ChronicIllnessInterface} implementation.
     *
     * @param theChronicIllnessDao The {@link ChronicIllnessInterface} implementation.
     */
    @Autowired
    public ChronicIllnessServiceImpl(@Qualifier("chronicIllnessDaoImpl") ChronicIllnessInterface theChronicIllnessDao) {
        this.chronicIllnessDao = theChronicIllnessDao;
    }

    /**
     * Creates a new chronic illness.
     *
     * @param chronicIllness The chronic illness to create.
     * @return The created chronic illness.
     * @throws MissingParameterException If a required parameter is missing in the new chronic illness.
     */
    @Override
    public ChronicIllness createChronicIllness(ChronicIllness chronicIllness) throws MissingParameterException {
        return chronicIllnessDao.createChronicIllness(chronicIllness);
    }

    /**
     * Retrieves all chronic illnesses.
     *
     * @return List of chronic illnesses.
     */
    @Override
    public Page<ChronicIllness> getAllChronicIllness(int page, int size, String sortBy, String search) {
        return chronicIllnessDao.getAllChronicIllness(page, size, sortBy, search);
    }

    @Override
    public List<ChronicIllness> getAllChronicIllnesses() {
        return chronicIllnessDao.getAllChronicIllnesses();
    }

    /**
     * Retrieves a chronic illness by its ID.
     *
     * @param id The ID of the chronic illness.
     * @return The chronic illness.
     * @throws DoesNotExistException If the chronic illness does not exist.
     */
    @Override
    public ChronicIllness getChronicIllness(int id) throws DoesNotExistException {
        return chronicIllnessDao.getChronicIllness(id);
    }

    /**
     * Updates a chronic illness with the given ID.
     *
     * @param id              The ID of the chronic illness to update.
     * @param chronicIllness  The updated chronic illness.
     * @return The updated chronic illness.
     * @throws DoesNotExistException    If the chronic illness does not exist.
     * @throws MissingParameterException If a required parameter is missing in the updated chronic illness.
     */
    @Override
    public ChronicIllness updateChronicIllness(int id, ChronicIllness chronicIllness) throws DoesNotExistException, MissingParameterException {
        return chronicIllnessDao.updateChronicIllness(id, chronicIllness);
    }

    /**
     * Deletes a chronic illness by its ID.
     *
     * @param id The ID of the chronic illness to delete.
     * @return {@code true} if the chronic illness is successfully deleted, {@code false} otherwise.
     * @throws DoesNotExistException If the chronic illness does not exist.
     */
    @Override
    public boolean deleteChronicIllness(int id) throws DoesNotExistException {
        return chronicIllnessDao.deleteChronicIllness(id);
    }
}
