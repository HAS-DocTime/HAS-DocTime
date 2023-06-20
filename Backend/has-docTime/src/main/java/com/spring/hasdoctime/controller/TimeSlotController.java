package com.spring.hasdoctime.controller;

import com.spring.hasdoctime.entity.TimeSlot;
import com.spring.hasdoctime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdoctime.interfaces.TimeSlotInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The TimeSlotController class handles HTTP requests related to the TimeSlot entity.
 */
@RestController
@RequestMapping("timeSlot")
@CrossOrigin(origins = "${port.address}")
public class TimeSlotController {

    private TimeSlotInterface timeSlotService;

    @Autowired
    public TimeSlotController(@Qualifier("timeSlotServiceImpl") TimeSlotInterface timeSlotService){
        this.timeSlotService = timeSlotService;
    }

    /**
     * Retrieves all time slots.
     *
     * @return ResponseEntity containing a list of all time slots and HttpStatus.OK if successful,
     * or HttpStatus.NOT_FOUND if no time slots are found.
     */
    @GetMapping
    public ResponseEntity<List<TimeSlot>> getAllTimeSlots() {
        try {
            List<TimeSlot> allTimeSlots = timeSlotService.getAllTimeSlots();
            return ResponseEntity.ok(allTimeSlots);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves a time slot by ID.
     *
     * @param id The ID of the time slot to retrieve.
     * @return ResponseEntity containing the retrieved time slot and HttpStatus.OK if successful.
     * @throws DoesNotExistException If the time slot with the given ID does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimeSlot> getTimeSlotById(@PathVariable int id) throws DoesNotExistException {
        try {
            TimeSlot timeSlot = timeSlotService.getTimeSlotById(id);
            return ResponseEntity.ok(timeSlot);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new time slot.
     *
     * @param timeSlot The time slot object to create.
     * @return The created time slot.
     * @throws MissingParameterException If required parameters are missing in the creation request.
     * @throws DoesNotExistException     If the time slot with the given ID does not exist.
     */
    @PostMapping
    public TimeSlot createTimeSlot(@Valid @RequestBody TimeSlot timeSlot) throws MissingParameterException, DoesNotExistException{
        return timeSlotService.createTimeSlot(timeSlot);
    }

    /**
     * Updates a time slot.
     *
     * @param id       The ID of the time slot to update.
     * @param timeSlot The updated time slot object.
     * @return ResponseEntity containing the updated time slot and HttpStatus.OK if successful,
     * or HttpStatus.NOT_FOUND if the time slot does not exist.
     * @throws DoesNotExistException     If the time slot with the given ID does not exist.
     * @throws MissingParameterException If required parameters are missing in the update request.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimeSlot> updateTimeSlot(@PathVariable int id, @Valid @RequestBody TimeSlot timeSlot) throws DoesNotExistException, MissingParameterException {
        try {
            TimeSlot updatedTimeSlot = timeSlotService.updateTimeSlot(id, timeSlot);
            return ResponseEntity.ok(updatedTimeSlot);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a time slot.
     *
     * @param id The ID of the time slot to delete.
     * @return ResponseEntity with HttpStatus.OK and a success message if the time slot is successfully deleted,
     * or HttpStatus.NOT_FOUND if the time slot does not exist.
     * @throws DoesNotExistException If the time slot with the given ID does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTimeSlot(@PathVariable int id) throws DoesNotExistException{
        String result = timeSlotService.deleteTimeSlot(id);
        if (result.equals("timeSlot with id: " + id + " is deleted")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
