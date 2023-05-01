package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.ChronicIllness;
import com.spring.hasdocTime.interfc.ChronicIllnessInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("chronicIllness")
@CrossOrigin(origins = "*")
public class ChronicIllnessController {

    private ChronicIllnessInterface chronicIllnessService;

    @Autowired
    public ChronicIllnessController(@Qualifier("chronicIllnessServiceImpl") ChronicIllnessInterface theChronicIllnessService) {
        this.chronicIllnessService = theChronicIllnessService;
    }

    @GetMapping("")
    public ResponseEntity<List<ChronicIllness>> getAllChronicIllness() {
        List<ChronicIllness> chronicIllnessList = chronicIllnessService.getAllChronicIllness();
        if(chronicIllnessList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity(chronicIllnessList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ChronicIllness> getSingleChronicIllness(@PathVariable("id") int id) {
        ChronicIllness chronicIllness = chronicIllnessService.getChronicIllness(id);
        if(chronicIllness == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity(chronicIllness, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ChronicIllness> createChronicIllness(@RequestBody ChronicIllness chronicIllness) {
        ChronicIllness theChronicIllness = chronicIllnessService.createChronicIllness(chronicIllness);
        return new ResponseEntity<>(theChronicIllness, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ChronicIllness> updateChronicIllness(@PathVariable("id") int id, @RequestBody ChronicIllness chronicIllness) {
        ChronicIllness theChronicIllness = chronicIllnessService.updateChronicIllness(id, chronicIllness);
        if(theChronicIllness == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity(theChronicIllness, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ChronicIllness> deleteChronicIllness(@PathVariable("id") int id) {
        if(chronicIllnessService.getChronicIllness(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        chronicIllnessService.deleteChronicIllness(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
