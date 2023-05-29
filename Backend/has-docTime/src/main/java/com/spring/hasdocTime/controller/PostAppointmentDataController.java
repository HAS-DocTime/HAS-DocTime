package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.PostAppointmentData;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.PostAppointmentDataInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("postAppointmentData")
@CrossOrigin(origins = "http://192.1.200.177:4200")
public class PostAppointmentDataController {

    private PostAppointmentDataInterface postAppointmentDataService;

    @Autowired
    public PostAppointmentDataController(@Qualifier("postAppointmentDataServiceImpl") PostAppointmentDataInterface postAppointmentDataService){
        this.postAppointmentDataService = postAppointmentDataService;
    }

    @GetMapping
    public ResponseEntity<List<PostAppointmentData>> getAllPostAppointmentData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "user.name") String sortBy,
            @RequestParam(required = false) String search
    ) {
            Page<PostAppointmentData> allPostAppointmentData = postAppointmentDataService.getAllPostAppointmentData(page, size, sortBy, search);
            if(allPostAppointmentData.isEmpty()){
                return new ResponseEntity<>(allPostAppointmentData.getContent(), HttpStatus.NO_CONTENT);
            }
            return ResponseEntity.ok(allPostAppointmentData.getContent());

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
    public ResponseEntity<List<PostAppointmentData>> getPostAppointmentDataByEmail(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "user.name") String sortBy,
            @RequestParam(required = false) String search
    ) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<PostAppointmentData> allPostAppointmentData = postAppointmentDataService.getPostAppointmentDataByEmail(userEmail, page, size, sortBy, search);
        if(allPostAppointmentData.isEmpty()) {
            return new ResponseEntity<>(allPostAppointmentData.getContent(), HttpStatus.NO_CONTENT);
            }
        return ResponseEntity.ok(allPostAppointmentData.getContent());
    }

    @PostMapping
    public PostAppointmentData createPostAppointmentData(@Valid @RequestBody PostAppointmentData postAppointmentData) throws MissingParameterException, DoesNotExistException {
        return postAppointmentDataService.createPostAppointmentData(postAppointmentData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostAppointmentData> updatePostAppointmentData(@PathVariable int id, @Valid @RequestBody PostAppointmentData postAppointmentData) throws DoesNotExistException, MissingParameterException {
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
    public ResponseEntity<List<Map<String, Integer>>> getDiseasesBySymptom(
            @PathVariable String symptom,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "disease") String sortBy,
            @RequestParam(required = false) String search
    ) throws DoesNotExistException{
        Page<Map<String, Integer>> diseaseData = postAppointmentDataService.getDiseasesGroupedBySymptom(symptom, page, size, sortBy, search);
        if(diseaseData.isEmpty()){
            return new ResponseEntity<>(diseaseData.getContent(), HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(diseaseData.getContent());
    }

    @GetMapping("data/{symptom}")
    public ResponseEntity<List<PostAppointmentData>> getPostAppointmentDataBySymptom(
            @PathVariable String symptom,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "user.name") String sortBy,
            @RequestParam(required = false) String search
    ) throws DoesNotExistException {
        Page<PostAppointmentData> appointmentData = postAppointmentDataService.getPostAppointmentDataBySymptom(symptom, page, size, sortBy, search);
        if(appointmentData.isEmpty()){
            return new ResponseEntity<>(appointmentData.getContent(), HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(appointmentData.getContent());
    }
    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<PostAppointmentData>> getPostAppointmentDataOfDoctor(
            @PathVariable int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "user.name") String sortBy,
            @RequestParam(required = false) String search
    ) throws DoesNotExistException{
        Page<PostAppointmentData> postAppointmentData = postAppointmentDataService.getPostAppointmentsDataOfDoctor(id, page, size, sortBy, search);
        return new ResponseEntity<>(postAppointmentData.getContent(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PostAppointmentData>> getPostAppointmentDataByUserId(
            @PathVariable int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "doctor.user.name") String sortBy,
            @RequestParam(required = false) String search
    ) throws DoesNotExistException {
        Page<PostAppointmentData> postAppointmentDataList = postAppointmentDataService.getPostAppointmentDataByUserId(id, page, size, sortBy, search);
        return new ResponseEntity<>(postAppointmentDataList.getContent(), HttpStatus.OK);
    }

}
