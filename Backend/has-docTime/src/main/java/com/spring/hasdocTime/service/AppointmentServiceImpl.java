package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.Appointment;
import com.spring.hasdocTime.interfc.AppointmentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentInterface {

    private AppointmentInterface appointmentDao;

    @Autowired
    public AppointmentServiceImpl(@Qualifier("appointmentDaoImpl") AppointmentInterface appointmentDao){
        this.appointmentDao = appointmentDao;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentDao.getAllAppointments();
    }

    @Override
    public Appointment getAppointmentById(int id) {
        return appointmentDao.getAppointmentById(id);
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        return appointmentDao.createAppointment(appointment);
    }

    @Override
    public Appointment updateAppointment(int id, Appointment appointment) {
        return appointmentDao.updateAppointment(id, appointment);
    }

    @Override
    public String deleteAppointment(int id) {
        return appointmentDao.deleteAppointment(id);
    }

    @Override
    public List<Appointment> getAppointmentsByUser(int userId) {
        return appointmentDao.getAppointmentsByUser(userId);
    }
}
