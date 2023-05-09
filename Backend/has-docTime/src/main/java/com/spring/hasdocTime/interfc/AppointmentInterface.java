package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.Appointment;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;

import java.util.List;

public interface AppointmentInterface {

    public List<Appointment> getAllAppointments();

    public Appointment getAppointmentById(int id) throws DoesNotExistException;

    public Appointment createAppointment(Appointment appointment) throws MissingParameterException;

    public Appointment updateAppointment(int id, Appointment appointment) throws DoesNotExistException, MissingParameterException;

    public Appointment deleteAppointment(int id) throws DoesNotExistException;

    public List<Appointment> getAppointmentsByUser(int userId) throws DoesNotExistException;

}
