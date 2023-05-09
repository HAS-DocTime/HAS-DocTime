package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.PostAppointmentData;
import com.spring.hasdocTime.interfc.PostAppointmentDataInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        try {
            List<PostAppointmentData> allPostAppointmentData = postAppointmentDataService.getAllPostAppointmentData();
            return ResponseEntity.ok(allPostAppointmentData);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<PostAppointmentData> getPostAppointmentDataById(@PathVariable int id) {
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
//        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        if(allPostAppointmentData.isEmpty()) {
            return new ResponseEntity<>(allPostAppointmentData, HttpStatus.NOT_FOUND);
            }
        return ResponseEntity.ok(allPostAppointmentData);
    }

    @PostMapping
    public PostAppointmentData createPostAppointmentData(@RequestBody PostAppointmentData postAppointmentData) {
        return postAppointmentDataService.createPostAppointmentData(postAppointmentData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostAppointmentData> updatePostAppointmentData(@PathVariable int id, @RequestBody PostAppointmentData postAppointmentData) {
        try {
            PostAppointmentData updatedPostAppointmentData = postAppointmentDataService.updatePostAppointmentData(id, postAppointmentData);
            return ResponseEntity.ok(updatedPostAppointmentData);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable int id) {
        String result = postAppointmentDataService.deletePostAppointmentData(id);
        if (result.equals("postAppointmentData with id: " + id + " is deleted")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
