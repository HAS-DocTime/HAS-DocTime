package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.ChronicIllness;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.ChronicIllnessInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("chronicIllness")
@CrossOrigin(origins = "${port.address}")
public class ChronicIllnessController {

    private ChronicIllnessInterface chronicIllnessService;

    @Autowired
    public ChronicIllnessController(@Qualifier("chronicIllnessServiceImpl") ChronicIllnessInterface theChronicIllnessService) {
        this.chronicIllnessService = theChronicIllnessService;
    }

    @GetMapping("")
    public ResponseEntity<Page<ChronicIllness>> getAllChronicIllness(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(required = false) String search
    ) {
        Page<ChronicIllness> chronicIllnessList = chronicIllnessService.getAllChronicIllness(page, size, sortBy, search);
        if(chronicIllnessList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity(chronicIllnessList, HttpStatus.OK);
    }

    @GetMapping("list")
    public ResponseEntity<List<ChronicIllness>> getAllChronicIllness() {
        List<ChronicIllness> chronicIllnessList = chronicIllnessService.getAllChronicIllnesses();
        if(chronicIllnessList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity(chronicIllnessList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ChronicIllness> getSingleChronicIllness(@PathVariable("id") int id) throws DoesNotExistException {
        ChronicIllness chronicIllness = chronicIllnessService.getChronicIllness(id);
        if(chronicIllness == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity(chronicIllness, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ChronicIllness> createChronicIllness(@Valid @RequestBody ChronicIllness chronicIllness) throws MissingParameterException{
        ChronicIllness theChronicIllness = chronicIllnessService.createChronicIllness(chronicIllness);
        return new ResponseEntity<>(theChronicIllness, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ChronicIllness> updateChronicIllness(@PathVariable("id") int id, @Valid @RequestBody ChronicIllness chronicIllness) throws DoesNotExistException, MissingParameterException {
        ChronicIllness theChronicIllness = chronicIllnessService.updateChronicIllness(id, chronicIllness);
        if(theChronicIllness == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity(theChronicIllness, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ChronicIllness> deleteChronicIllness(@PathVariable("id") int id) throws DoesNotExistException{
        chronicIllnessService.deleteChronicIllness(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
