package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.TimeSlot;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.TimeSlotInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the TimeSlotInterface for handling time slot-related functionality.
 */
@Service
public class TimeSlotServiceImpl implements TimeSlotInterface {

    private TimeSlotInterface timeSlotDao;

    @Autowired
    public TimeSlotServiceImpl(@Qualifier("timeSlotDaoImpl") TimeSlotInterface timeSlotDao){
        this.timeSlotDao = timeSlotDao;
    }

    /**
     * Retrieves all time slots.
     *
     * @return The list of time slots.
     */
    @Override
    public List<TimeSlot> getAllTimeSlots() {
        return timeSlotDao.getAllTimeSlots();
    }

    /**
     * Retrieves a time slot by ID.
     *
     * @param id The ID of the time slot.
     * @return The time slot.
     * @throws DoesNotExistException If the time slot does not exist.
     */
    @Override
    public TimeSlot getTimeSlotById(int id) throws DoesNotExistException {
        return timeSlotDao.getTimeSlotById(id);
    }

    /**
     * Creates a new time slot.
     *
     * @param timeSlot The time slot to create.
     * @return The created time slot.
     * @throws DoesNotExistException     If the time slot does not exist.
     * @throws MissingParameterException If a required parameter is missing.
     */
    @Override
    public TimeSlot createTimeSlot(TimeSlot timeSlot) throws MissingParameterException, DoesNotExistException{
        return timeSlotDao.createTimeSlot(timeSlot);
    }

    /**
     * Updates a time slot.
     *
     * @param id        The ID of the time slot to update.
     * @param timeSlot The updated time slot.
     * @return The updated time slot.
     * @throws DoesNotExistException     If the time slot does not exist.
     * @throws MissingParameterException If a required parameter is missing.
     */
    @Override
    public TimeSlot updateTimeSlot(int id, TimeSlot timeSlot) throws DoesNotExistException, MissingParameterException {
        return timeSlotDao.updateTimeSlot(id, timeSlot);
    }

    /**
     * Deletes a time slot.
     *
     * @param id The ID of the time slot to delete.
     * @return A string indicating the status of the deletion.
     * @throws DoesNotExistException If the time slot does not exist.
     */
    @Override
    public String deleteTimeSlot(int id) throws DoesNotExistException{
        return timeSlotDao.deleteTimeSlot(id);
    }
}
