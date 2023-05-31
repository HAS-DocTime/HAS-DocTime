/**
 * Interface defining operations for time slots.
 */
package com.spring.hasdocTime.interfaces;

import com.spring.hasdocTime.entity.TimeSlot;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;

import java.util.List;

public interface TimeSlotInterface {

    /**
     * Retrieves all time slots.
     *
     * @return a list of TimeSlot objects representing all time slots
     */
    List<TimeSlot> getAllTimeSlots();

    /**
     * Retrieves a time slot by ID.
     *
     * @param id the ID of the time slot
     * @return the TimeSlot object representing the retrieved time slot
     * @throws DoesNotExistException if the time slot does not exist
     */
    TimeSlot getTimeSlotById(int id) throws DoesNotExistException;

    /**
     * Creates a new time slot.
     *
     * @param timeSlot the TimeSlot object representing the time slot to be created
     * @return the TimeSlot object representing the created time slot
     * @throws DoesNotExistException    if related entities do not exist
     * @throws MissingParameterException if required parameters are missing
     */
    TimeSlot createTimeSlot(TimeSlot timeSlot) throws MissingParameterException, DoesNotExistException;

    /**
     * Updates a time slot.
     *
     * @param id       the ID of the time slot to be updated
     * @param timeSlot the TimeSlot object representing the updated time slot
     * @return the TimeSlot object representing the updated time slot
     * @throws DoesNotExistException    if the time slot does not exist
     * @throws MissingParameterException if required parameters are missing
     */
    TimeSlot updateTimeSlot(int id, TimeSlot timeSlot) throws DoesNotExistException, MissingParameterException;

    /**
     * Deletes a time slot.
     *
     * @param id the ID of the time slot to be deleted
     * @return a string indicating the result of the deletion
     * @throws DoesNotExistException if the time slot does not exist
     */
    String deleteTimeSlot(int id) throws DoesNotExistException;

}
