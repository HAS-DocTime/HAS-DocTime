package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.Symptom;
import com.spring.hasdocTime.interfc.SymptomInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/symptom")
@CrossOrigin(value = "*")
public class SymptomController {

    @Autowired
    @Qualifier("symptomServiceImpl")
    private SymptomInterface symptomService;

    @GetMapping("")
    public List<Symptom> getAllSymptom(){
        return symptomService.getAllSymptom();
    }

    @GetMapping("{id}")
    public Symptom getSymptom(@PathVariable int id){
        return symptomService.getSymptom(id);
    }

    @PutMapping("{id}")
    public Symptom updateSymptom(@PathVariable int id, @RequestBody Symptom symptom){
        return symptomService.updateSymptom(id, symptom);
    }

    @DeleteMapping("{id}")
    public boolean deleteSymptom(@PathVariable int id){
        return symptomService.deleteSymptom(id);
    }

    @PostMapping("")
    public Symptom createSymptom(@RequestBody Symptom symptom){
        return symptomService.createSymptom(symptom);
    }

}