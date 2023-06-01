package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.ChronicIllness;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.ChronicIllnessInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The ChronicIllnessController class handles HTTP requests related to the ChronicIllness entity.
 */
@RestController
@RequestMapping("chronicIllness")
@CrossOrigin(origins = "${port.address}")
public class ChronicIllnessController {

    private ChronicIllnessInterface chronicIllnessService;

    /**
     * Constructs an instance of ChronicIllnessController with the specified ChronicIllnessService implementation.
     *
     * @param theChronicIllnessService The ChronicIllnessService implementation to use.
     */
    @Autowired
    public ChronicIllnessController(@Qualifier("chronicIllnessServiceImpl") ChronicIllnessInterface theChronicIllnessService) {
        this.chronicIllnessService = theChronicIllnessService;
    }

    /**
     * Retrieves all ChronicIllnesses.
     *
     * @return ResponseEntity containing a list of all ChronicIllnesses and HttpStatus.OK if successful,
     * or HttpStatus.NO_CONTENT if no ChronicIllnesses are found.
     */
    @GetMapping("")
    public ResponseEntity<List<ChronicIllness>> getAllChronicIllness() {
        List<ChronicIllness> chronicIllnessList = chronicIllnessService.getAllChronicIllness();
        if(chronicIllnessList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return new ResponseEntity<>(chronicIllnessList, HttpStatus.OK);
    }

    /**
     * Retrieves a single ChronicIllness by its ID.
     *
     * @param id The ID of the ChronicIllness to retrieve.
     * @return ResponseEntity containing the retrieved ChronicIllness and HttpStatus.OK if successful,
     * or HttpStatus.NOT_FOUND if the ChronicIllness does not exist.
     * @throws DoesNotExistException If the ChronicIllness with the given ID does not exist.
     */
    @GetMapping("{id}")
    public ResponseEntity<ChronicIllness> getSingleChronicIllness(@PathVariable("id") int id) throws DoesNotExistException {
        ChronicIllness chronicIllness = chronicIllnessService.getChronicIllness(id);
        if (chronicIllness == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(chronicIllness, HttpStatus.OK);
    }

    /**
     * Creates a new ChronicIllness.
     *
     * @param chronicIllness The ChronicIllness object to create.
     * @return ResponseEntity containing the created ChronicIllness and HttpStatus.CREATED if successful.
     * @throws MissingParameterException If required parameters are missing in the creation request.
     */
    @PostMapping("")
    public ResponseEntity<ChronicIllness> createChronicIllness(@Valid @RequestBody ChronicIllness chronicIllness) throws MissingParameterException {
        ChronicIllness theChronicIllness = chronicIllnessService.createChronicIllness(chronicIllness);
        return new ResponseEntity<>(theChronicIllness, HttpStatus.CREATED);
    }

    /**
     * Updates a ChronicIllness.
     *
     * @param id             The ID of the ChronicIllness to update.
     * @param chronicIllness The updated ChronicIllness object.
     * @return ResponseEntity containing the updated ChronicIllness and HttpStatus.OK if successful,
     * or HttpStatus.NOT_FOUND if the ChronicIllness does not exist.
     * @throws DoesNotExistException     If the ChronicIllness with the given ID does not exist.
     * @throws MissingParameterException If required parameters are missing in the update request.
     */
    @PutMapping("{id}")
    public ResponseEntity<ChronicIllness> updateChronicIllness(@PathVariable("id") int id, @Valid @RequestBody ChronicIllness chronicIllness) throws DoesNotExistException, MissingParameterException {
        ChronicIllness theChronicIllness = chronicIllnessService.updateChronicIllness(id, chronicIllness);
        if (theChronicIllness == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(theChronicIllness, HttpStatus.OK);
    }

    /**
     * Deletes a ChronicIllness.
     *
     * @param id The ID of the ChronicIllness to delete.
     * @return ResponseEntity with HttpStatus.OK if the ChronicIllness is successfully deleted.
     * @throws DoesNotExistException If the ChronicIllness with the given ID does not exist.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<ChronicIllness> deleteChronicIllness(@PathVariable("id") int id) throws DoesNotExistException {
        chronicIllnessService.deleteChronicIllness(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
