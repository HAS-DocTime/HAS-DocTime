package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.Appointment;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.AppointmentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
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
    public Page<Appointment> getAllAppointments(int page, int size, String sortBy, String search) {
        return appointmentDao.getAllAppointments(page, size, sortBy, search);
    }

    @Override
    public Appointment getAppointmentById(int id) throws DoesNotExistException {
        return appointmentDao.getAppointmentById(id);
    }

    @Override
    public Appointment createAppointment(Appointment appointment) throws MissingParameterException, DoesNotExistException {
        return appointmentDao.createAppointment(appointment);
    }

    @Override
    public Appointment updateAppointment(int id, Appointment appointment) throws DoesNotExistException, MissingParameterException {
        return appointmentDao.updateAppointment(id, appointment);
    }

    @Override
    public Appointment deleteAppointment(int id) throws DoesNotExistException{
        return appointmentDao.deleteAppointment(id);
    }

    @Override
    public Page<Appointment> getAppointmentsByUser(int userId, int page, int size, String sortBy, String search) throws DoesNotExistException{
        return appointmentDao.getAppointmentsByUser(userId, page, size, sortBy, search);
    }

    @Override
    public Page<Appointment> getAppointmentsOfDoctor(int id, int page, int size, String sortBy, String search) throws DoesNotExistException {
        return appointmentDao.getAppointmentsOfDoctor(id, page, size, sortBy, search);
    }
}
