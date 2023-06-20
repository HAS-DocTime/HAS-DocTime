package com.spring.hasdoctime.dao;


import com.spring.hasdoctime.constants.Constant;
import com.spring.hasdoctime.entity.ChronicIllness;
import com.spring.hasdoctime.entity.PatientChronicIllness;
import com.spring.hasdoctime.exceptionhandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionhandling.exception.MissingParameterException;
import com.spring.hasdoctime.interfaces.ChronicIllnessInterface;
import com.spring.hasdoctime.interfaces.PatientChronicIllnessInterface;
import com.spring.hasdoctime.repository.ChronicIllnessRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the ChronicIllnessInterface for performing CRUD operations on ChronicIllness entities.
 */
@Service
public class ChronicIllnessDaoImpl implements ChronicIllnessInterface {


    private ChronicIllnessRepository chronicIllnessRepository;
    private PatientChronicIllnessInterface patientChronicIllnessDao;

    /**
     * Constructs a ChronicIllnessDaoImpl with the specified repositories and interfaces.
     *
     * @param theChronicIllnessRepository The repository for ChronicIllness entities.
     * @param patientChronicIllnessDao     The interface for PatientChronicIllness operations.
     */
    @Autowired
    public ChronicIllnessDaoImpl(ChronicIllnessRepository theChronicIllnessRepository, PatientChronicIllnessInterface patientChronicIllnessDao){
        this.patientChronicIllnessDao = patientChronicIllnessDao;
        this.chronicIllnessRepository = theChronicIllnessRepository;
    }

    /**
     * Creates a new ChronicIllness.
     *
     * @param chronicIllness The ChronicIllness to create.
     * @return The created ChronicIllness.
     * @throws MissingParameterException if the name of the ChronicIllness is missing.
     */
    @Transactional
    @Override
    public ChronicIllness createChronicIllness(ChronicIllness chronicIllness) throws MissingParameterException{
        if(chronicIllness.getName()==null || chronicIllness.getName().equals("")){
            throw new MissingParameterException(Constant.CHRONIC_ILLNESS);
        }
        return chronicIllnessRepository.save(chronicIllness);
    }

    /**
     * Retrieves all ChronicIllness entities.
     *
     * @return The list of all ChronicIllness entities.
     */
    @Override
    public Page<ChronicIllness> getAllChronicIllness(int page, int size, String sortBy, String search){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<ChronicIllness> chronicIllnessPage;
        if(search != null && !search.isEmpty()){
            chronicIllnessPage = chronicIllnessRepository.findAllAndNameContainsIgnoreCase(search, pageable);
        } else {
            chronicIllnessPage = chronicIllnessRepository.findAll(pageable);
        }
        for(ChronicIllness chronicIllness : chronicIllnessPage){
            Hibernate.initialize(chronicIllness.getPatientChronicIllnesses());
        }
        return chronicIllnessPage;
    }

    @Override
    public List<ChronicIllness> getAllChronicIllnesses() {
        return chronicIllnessRepository.findAll();
    }


    /**
     * Retrieves a ChronicIllness by its ID.
     *
     * @param id The ID of the ChronicIllness to retrieve.
     * @return The retrieved ChronicIllness.
     * @throws DoesNotExistException if the ChronicIllness with the specified ID does not exist.
     */
    @Override
    public ChronicIllness getChronicIllness(int id) throws DoesNotExistException {
        Optional<ChronicIllness> optionalChronicIllness = chronicIllnessRepository.findById(id);
        if(optionalChronicIllness.isPresent()) {
            return optionalChronicIllness.get();
        }
        throw new DoesNotExistException(Constant.CHRONIC_ILLNESS);
    }

    /**
     * Updates a ChronicIllness.
     *
     * @param id             The ID of the ChronicIllness to update.
     * @param chronicIllness The updated ChronicIllness.
     * @return The updated ChronicIllness.
     * @throws DoesNotExistException     if the ChronicIllness with the specified ID does not exist.
     * @throws MissingParameterException if the name of the ChronicIllness is missing.
     */
    @Transactional
    @Override
    public ChronicIllness updateChronicIllness(int id, ChronicIllness chronicIllness) throws DoesNotExistException, MissingParameterException {
        if(chronicIllness.getName()==null || chronicIllness.getName().equals("")){
            throw new MissingParameterException(Constant.CHRONIC_ILLNESS);
        }
        Optional<ChronicIllness> optionalChronicIllness = chronicIllnessRepository.findById(id);
        if(optionalChronicIllness.isPresent()){
            ChronicIllness oldChronicIllness = optionalChronicIllness.get();
            chronicIllness.setId(oldChronicIllness.getId());
            chronicIllnessRepository.save(chronicIllness);
            return chronicIllness;
        }
        throw new DoesNotExistException("Chronic Illness");
    }

    /**
     * Deletes a ChronicIllness by its ID.
     *
     * @param id The ID of the ChronicIllness to delete.
     * @return true if the ChronicIllness was successfully deleted, false otherwise.
     * @throws DoesNotExistException if the ChronicIllness with the specified ID does not exist.
     */
    @Transactional
    @Override
    public boolean deleteChronicIllness(int id) throws DoesNotExistException{
        Optional<ChronicIllness> optionalChronicIllness = chronicIllnessRepository.findById(id);
        if(optionalChronicIllness.isPresent()) {
            for(PatientChronicIllness patientChronicIllness : optionalChronicIllness.get().getPatientChronicIllnesses()){
                patientChronicIllnessDao.deletePatientChronicIllness(patientChronicIllness.getId());
            }
            
            chronicIllnessRepository.deleteById(id);
            return true;
        }
        throw new DoesNotExistException("Chronic Illness");
    }
}
