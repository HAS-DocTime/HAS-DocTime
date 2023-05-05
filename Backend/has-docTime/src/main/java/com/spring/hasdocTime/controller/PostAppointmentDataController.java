package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.PostAppointmentData;
import com.spring.hasdocTime.interfc.PostAppointmentDataInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("id/{id}")
    public ResponseEntity<PostAppointmentData> getPostAppointmentDataById(@PathVariable int id) {
        try {
            PostAppointmentData postAppointmentData = postAppointmentDataService.getPostAppointmentDataById(id);
            return ResponseEntity.ok(postAppointmentData);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("email/{email}")
    public ResponseEntity<List<PostAppointmentData>> getPostAppointmentDataByEmail(@PathVariable String email) {
        try {
            List<PostAppointmentData> allPostAppointmentData = postAppointmentDataService.getPostAppointmentDataByEmail(email);
            return ResponseEntity.ok(allPostAppointmentData);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
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
