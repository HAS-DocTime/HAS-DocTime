package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.Department;
import com.spring.hasdocTime.entity.TimeSlot;

import java.util.List;

public interface TimeSlotInterface {

    public List<TimeSlot> getAllTimeSlots();

    public TimeSlot getTimeSlotById(int id);

    public TimeSlot createTimeSlot(TimeSlot timeSlot);

    public TimeSlot updateTimeSlot(int id, TimeSlot timeSlot);

    public String deleteTimeSlot(int id);

}
