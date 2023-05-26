package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.Symptom;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.SymptomInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/symptom")
@CrossOrigin(origins = "http://192.1.200.29:4200")
public class SymptomController {

    @Autowired
    @Qualifier("symptomServiceImpl")
    private SymptomInterface symptomService;

    @GetMapping("")
    public List<Symptom> getAllSymptom(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(required = false) String search
    ){
        Page<Symptom> symptoms = symptomService.getAllSymptom(page, size, sortBy, search);
        return symptoms.getContent();
    }

    @GetMapping("{id}")
    public Symptom getSymptom(@PathVariable int id) throws DoesNotExistException{
        return symptomService.getSymptom(id);
    }

    @PutMapping("{id}")
    public Symptom updateSymptom(@PathVariable int id, @RequestBody Symptom symptom) throws DoesNotExistException, MissingParameterException {
        return symptomService.updateSymptom(id, symptom);
    }

    @DeleteMapping("{id}")
    public boolean deleteSymptom(@PathVariable int id) throws DoesNotExistException{
        return symptomService.deleteSymptom(id);
    }

    @PostMapping("")
    public Symptom createSymptom(@RequestBody Symptom symptom) throws DoesNotExistException, MissingParameterException{
        return symptomService.createSymptom(symptom);
    }

}