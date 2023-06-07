package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.Symptom;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.SymptomInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the SymptomInterface for handling symptom-related functionality.
 */
@Service
public class SymptomServiceImpl implements SymptomInterface {

    @Autowired
    @Qualifier("symptomDaoImpl")
    private SymptomInterface symptomDao;

    /**
     * Retrieves a symptom by ID.
     *
     * @param id The ID of the symptom.
     * @return The symptom.
     * @throws DoesNotExistException If the symptom does not exist.
     */
    @Override
    public Symptom getSymptom(int id) throws DoesNotExistException {
        return symptomDao.getSymptom(id);
    }

    /**
     * Retrieves all symptoms.
     *
     * @return The list of symptoms.
     */
    @Override
    public Page<Symptom> getAllSymptom(int page, int size, String sortBy, String search) {
        return symptomDao.getAllSymptom(page, size, sortBy, search);
    }

    @Override
    public List<Symptom> getAllSymptomList() {
        return symptomDao.getAllSymptomList();
    }

    /**
     * Creates a new symptom.
     *
     * @param symptom The symptom to create.
     * @return The created symptom.
     * @throws DoesNotExistException     If the symptom does not exist.
     * @throws MissingParameterException If a required parameter is missing.
     */
    @Override
    public Symptom createSymptom(Symptom symptom) throws DoesNotExistException, MissingParameterException {
        return symptomDao.createSymptom(symptom);
    }

    /**
     * Updates a symptom.
     *
     * @param id      The ID of the symptom to update.
     * @param symptom The updated symptom.
     * @return The updated symptom.
     * @throws DoesNotExistException     If the symptom does not exist.
     * @throws MissingParameterException If a required parameter is missing.
     */
    @Override
    public Symptom updateSymptom(int id, Symptom symptom) throws DoesNotExistException, MissingParameterException {
        return symptomDao.updateSymptom(id, symptom);
    }

    /**
     * Deletes a symptom.
     *
     * @param id The ID of the symptom to delete.
     * @return True if the deletion is successful, false otherwise.
     * @throws DoesNotExistException If the symptom does not exist.
     */
    @Override
    public boolean deleteSymptom(int id) throws DoesNotExistException {
        return symptomDao.deleteSymptom(id);
    }
}
