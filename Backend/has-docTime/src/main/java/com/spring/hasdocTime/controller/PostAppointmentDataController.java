package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.PostAppointmentData;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.PostAppointmentDataInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("postAppointmentData")
@CrossOrigin(origins = "*")
public class PostAppointmentDataController {

    private PostAppointmentDataInterface postAppointmentDataService;

    @Autowired
    public PostAppointmentDataController(@Qualifier("postAppointmentDataServiceImpl") PostAppointmentDataInterface postAppointmentDataService){
        this.postAppointmentDataService = postAppointmentDataService;
    }

    @GetMapping
    public ResponseEntity<List<PostAppointmentData>> getAllPostAppointmentData() {
            List<PostAppointmentData> allPostAppointmentData = postAppointmentDataService.getAllPostAppointmentData();
            if(allPostAppointmentData.isEmpty()){
                return new ResponseEntity<>(allPostAppointmentData, HttpStatus.NO_CONTENT);
            }
            return ResponseEntity.ok(allPostAppointmentData);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PostAppointmentData> getPostAppointmentDataById(@PathVariable int id) throws DoesNotExistException{
            PostAppointmentData postAppointmentData = postAppointmentDataService.getPostAppointmentDataById(id);
            if(postAppointmentData==null){
                return new ResponseEntity<>(postAppointmentData, HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(postAppointmentData);
    }

    @GetMapping("findByUserEmail")
    public ResponseEntity<List<PostAppointmentData>> getPostAppointmentDataByEmail() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        List<PostAppointmentData> allPostAppointmentData = postAppointmentDataService.getPostAppointmentDataByEmail(userEmail);
        return ResponseEntity.ok(allPostAppointmentData);
    }

    @PostMapping
    public PostAppointmentData createPostAppointmentData(@RequestBody PostAppointmentData postAppointmentData) throws MissingParameterException, DoesNotExistException {
        return postAppointmentDataService.createPostAppointmentData(postAppointmentData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostAppointmentData> updatePostAppointmentData(@PathVariable int id, @RequestBody PostAppointmentData postAppointmentData) throws DoesNotExistException, MissingParameterException {
        try {
            PostAppointmentData updatedPostAppointmentData = postAppointmentDataService.updatePostAppointmentData(id, postAppointmentData);
            return ResponseEntity.ok(updatedPostAppointmentData);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable int id) throws DoesNotExistException{
        String result = postAppointmentDataService.deletePostAppointmentData(id);
        if (result.equals("postAppointmentData with id: " + id + " is deleted")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("disease/{symptom}")
    public ResponseEntity<List<Map<String, Integer>>> getDiseasesBySymptom(@PathVariable String symptom) throws DoesNotExistException{
        List<Map<String, Integer>> diseaseData = postAppointmentDataService.getDiseasesGroupedBySymptom(symptom);
        if(diseaseData.isEmpty()){
            return new ResponseEntity<>(diseaseData, HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(diseaseData);
    }

    @GetMapping("data/{symptom}")
    public ResponseEntity<List<PostAppointmentData>> getPostAppointmentDataBySymptom(@PathVariable String symptom) throws DoesNotExistException {
        List<PostAppointmentData> appointmentData = postAppointmentDataService.getPostAppointmentDataBySymptom(symptom);
        if(appointmentData.isEmpty()){
            return new ResponseEntity<>(appointmentData, HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(appointmentData);
    }
}
