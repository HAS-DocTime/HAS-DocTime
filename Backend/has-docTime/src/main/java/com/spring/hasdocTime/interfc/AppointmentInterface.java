package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.Appointment;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AppointmentInterface {

    public Page<Appointment> getAllAppointments(int page, int size, String sortBy, String search);

    public Appointment getAppointmentById(int id) throws DoesNotExistException;

    public Appointment createAppointment(Appointment appointment) throws MissingParameterException, DoesNotExistException;

    public Appointment updateAppointment(int id, Appointment appointment) throws DoesNotExistException, MissingParameterException;

    public Appointment deleteAppointment(int id) throws DoesNotExistException;

    public Page<Appointment> getAppointmentsByUser(int userId, int page, int size, String sortBy, String search) throws DoesNotExistException;

    public Page<Appointment> getAppointmentsOfDoctor( int id, int page, int size, String sortBy, String search) throws DoesNotExistException;

}
