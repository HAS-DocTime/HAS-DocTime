package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.TimeSlot;
import com.spring.hasdocTime.interfc.TimeSlotInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeSlotServiceImpl implements TimeSlotInterface {

    private TimeSlotInterface timeSlotDao;

    @Autowired
    public TimeSlotServiceImpl(@Qualifier("timeSlotDaoImpl") TimeSlotInterface timeSlotDao){
        this.timeSlotDao = timeSlotDao;
    }

    @Override
    public List<TimeSlot> getAllTimeSlots() {
        return timeSlotDao.getAllTimeSlots();
    }

    @Override
    public TimeSlot getTimeSlotById(int id) {
        return timeSlotDao.getTimeSlotById(id);
    }

    @Override
    public TimeSlot createTimeSlot(TimeSlot timeSlot) {
        return timeSlotDao.createTimeSlot(timeSlot);
    }

    @Override
    public TimeSlot updateTimeSlot(int id, TimeSlot timeSlot) {
        return timeSlotDao.updateTimeSlot(id, timeSlot);
    }

    @Override
    public String deleteTimeSlot(int id) {
        return timeSlotDao.deleteTimeSlot(id);
    }
}
