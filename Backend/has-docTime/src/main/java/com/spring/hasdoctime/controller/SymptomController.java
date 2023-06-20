package com.spring.hasdoctime.controller;

import com.spring.hasdoctime.entity.Symptom;
import com.spring.hasdoctime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdoctime.interfaces.SymptomInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The SymptomController class handles HTTP requests related to the Symptom entity.
 */
@RestController
@RequestMapping("/symptom")
@CrossOrigin(origins = "${port.address}")
public class SymptomController {

    @Autowired
    @Qualifier("symptomServiceImpl")
    private SymptomInterface symptomService;

    /**
     * Retrieves all symptoms.
     *
     * @return A list of all symptoms.
     */
    @GetMapping("")
    public Page<Symptom> getAllSymptom(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,     //sortBy name only
            @RequestParam(required = false) String search
    ){
        Page<Symptom> symptoms = symptomService.getAllSymptom(page, size, sortBy, search);
        return symptoms;
    }

    @GetMapping("list")
    public List<Symptom> getAllSymptom(){
        return symptomService.getAllSymptomList();
    }


    /**
     * Retrieves a symptom by ID.
     *
     * @param id The ID of the symptom to retrieve.
     * @return The retrieved symptom.
     * @throws DoesNotExistException If the symptom with the given ID does not exist.
     */
    @GetMapping("{id}")
    public Symptom getSymptom(@PathVariable int id) throws DoesNotExistException{
        return symptomService.getSymptom(id);
    }

    /**
     * Updates a symptom.
     *
     * @param id      The ID of the symptom to update.
     * @param symptom The updated symptom object.
     * @return The updated symptom.
     * @throws DoesNotExistException     If the symptom with the given ID does not exist.
     * @throws MissingParameterException If required parameters are missing in the update request.
     */
    @PutMapping("{id}")
    public Symptom updateSymptom(@PathVariable int id, @Valid @RequestBody Symptom symptom) throws DoesNotExistException, MissingParameterException {
        return symptomService.updateSymptom(id, symptom);
    }

    /**
     * Deletes a symptom.
     *
     * @param id The ID of the symptom to delete.
     * @return True if the symptom is successfully deleted, false otherwise.
     * @throws DoesNotExistException If the symptom with the given ID does not exist.
     */
    @DeleteMapping("{id}")
    public boolean deleteSymptom(@PathVariable int id) throws DoesNotExistException{
        return symptomService.deleteSymptom(id);
    }

    /**
     * Creates a new symptom.
     *
     * @param symptom The symptom object to create.
     * @return The created symptom.
     * @throws DoesNotExistException     If the symptom with the given ID does not exist.
     * @throws MissingParameterException If required parameters are missing in the creation request.
     */
    @PostMapping("")
    public Symptom createSymptom(@Valid @RequestBody Symptom symptom) throws DoesNotExistException, MissingParameterException{
        return symptomService.createSymptom(symptom);
    }
}
