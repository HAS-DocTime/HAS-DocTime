package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.TimeSlot;
import com.spring.hasdocTime.interfc.TimeSlotInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("timeSlot")
@CrossOrigin(value = "*")
public class TimeSlotController {

    private TimeSlotInterface timeSlotService;

    @Autowired
    public TimeSlotController(@Qualifier("timeSlotServiceImpl") TimeSlotInterface timeSlotService){
        this.timeSlotService = timeSlotService;
    }

    @GetMapping
    public ResponseEntity<List<TimeSlot>> getAllTimeSlots() {
        try {
            List<TimeSlot> allTimeSlots = timeSlotService.getAllTimeSlots();
            return ResponseEntity.ok(allTimeSlots);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeSlot> getTimeSlotById(@PathVariable int id) {
        try {
            TimeSlot timeSlot = timeSlotService.getTimeSlotById(id);
            return ResponseEntity.ok(timeSlot);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public TimeSlot createTimeSlot(@RequestBody TimeSlot TimeSlot) {
        return timeSlotService.createTimeSlot(TimeSlot);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeSlot> updateTimeSlot(@PathVariable int id, @RequestBody TimeSlot timeSlot) {
        try {
            TimeSlot updatedTimeSlot = timeSlotService.updateTimeSlot(id, timeSlot);
            return ResponseEntity.ok(updatedTimeSlot);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTimeSlot(@PathVariable int id) {
        String result = timeSlotService.deleteTimeSlot(id);
        if (result.equals("timeSlot with id: " + id + " is deleted")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
