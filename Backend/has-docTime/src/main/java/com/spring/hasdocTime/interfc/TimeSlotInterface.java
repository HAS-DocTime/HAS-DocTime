package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.Department;
import com.spring.hasdocTime.entity.TimeSlot;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;

import java.util.List;

public interface TimeSlotInterface {

    public List<TimeSlot> getAllTimeSlots();

    public TimeSlot getTimeSlotById(int id) throws DoesNotExistException;

    public TimeSlot createTimeSlot(TimeSlot timeSlot) throws MissingParameterException;

    public TimeSlot updateTimeSlot(int id, TimeSlot timeSlot) throws DoesNotExistException, MissingParameterException;

    public String deleteTimeSlot(int id) throws DoesNotExistException;

}
