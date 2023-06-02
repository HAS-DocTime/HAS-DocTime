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

/**
 * Implementation of the {@link AppointmentInterface} interface that provides the business logic for managing appointments.
 */
@Service
public class AppointmentServiceImpl implements AppointmentInterface {

    private AppointmentInterface appointmentDao;

    /**
     * Constructs a new {@code AppointmentServiceImpl} with the specified {@link AppointmentInterface} implementation.
     *
     * @param appointmentDao The {@link AppointmentInterface} implementation.
     */
    @Autowired
    public AppointmentServiceImpl(@Qualifier("appointmentDaoImpl") AppointmentInterface appointmentDao){
        this.appointmentDao = appointmentDao;
    }

    /**
     * Retrieves all appointments.
     *
     * @return List of appointments.
     */
    @Override
    public Page<Appointment> getAllAppointments(int page, int size, String sortBy, String search) {
        return appointmentDao.getAllAppointments(page, size, sortBy, search);
    }

    /**
     * Retrieves an appointment by its ID.
     *
     * @param id The ID of the appointment.
     * @return The appointment.
     * @throws DoesNotExistException If the appointment does not exist.
     */
    @Override
    public Appointment getAppointmentById(int id) throws DoesNotExistException {
        return appointmentDao.getAppointmentById(id);
    }

    /**
     * Creates a new appointment.
     *
     * @param appointment The appointment to create.
     * @return The created appointment.
     * @throws MissingParameterException If a required parameter is missing in the new appointment.
     * @throws DoesNotExistException    If the appointment does not exist.
     */
    @Override
    public Appointment createAppointment(Appointment appointment) throws MissingParameterException, DoesNotExistException {
        return appointmentDao.createAppointment(appointment);
    }

    /**
     * Updates an appointment with the given ID.
     *
     * @param id          The ID of the appointment to update.
     * @param appointment The updated appointment.
     * @return The updated appointment.
     * @throws DoesNotExistException    If the appointment does not exist.
     * @throws MissingParameterException If a required parameter is missing in the updated appointment.
     */
    @Override
    public Appointment updateAppointment(int id, Appointment appointment) throws DoesNotExistException, MissingParameterException {
        return appointmentDao.updateAppointment(id, appointment);
    }

    /**
     * Deletes an appointment by its ID.
     *
     * @param id The ID of the appointment to delete.
     * @return The deleted appointment.
     * @throws DoesNotExistException If the appointment does not exist.
     */
    @Override
    public Appointment deleteAppointment(int id) throws DoesNotExistException{
        return appointmentDao.deleteAppointment(id);
    }

    /**
     * Retrieves all appointments associated with a user.
     *
     * @param userId The ID of the user.
     * @return List of appointments associated with the user.
     * @throws DoesNotExistException If the user does not exist.
     */
    @Override
    public Page<Appointment> getAppointmentsByUser(int userId, int page, int size, String sortBy, String search) throws DoesNotExistException{
        return appointmentDao.getAppointmentsByUser(userId, page, size, sortBy, search);
    }

    /**
     * Retrieves all appointments associated with a doctor.
     *
     * @param id The ID of the doctor.
     * @return List of appointments associated with the doctor.
     * @throws DoesNotExistException If the doctor does not exist.
     */
    @Override
    public List<Appointment> getAppointmentListByUser(int userId) throws DoesNotExistException {
        return appointmentDao.getAppointmentListByUser(userId);
    }

    @Override
    public Page<Appointment> getAppointmentsOfDoctor(int id, int page, int size, String sortBy, String search) throws DoesNotExistException {
        return appointmentDao.getAppointmentsOfDoctor(id, page, size, sortBy, search);
    }
}
